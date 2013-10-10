package de.andrena.kickercam.youtube;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;

import de.andrena.kickercam.goal.VideoUploader;

/**
 * Taken from
 * https://code.google.com/p/youtube-api-samples/source/browse/samples
 * /java/youtube
 * -cmdline-uploadvideo-sample/src/main/java/com/google/api/services
 * /samples/youtube/cmdline/youtube_cmdline_uploadvideo_sample/UploadVideo.java
 */
public class YoutubeVideoUploader implements VideoUploader {
	private static final int LOCAL_RECEIVER_PORT = 9000;
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private static String VIDEO_FILE_FORMAT = "video/*";
	private final File workingDirectory;

	public YoutubeVideoUploader(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	private Credential authorize(List<String> scopes) throws IOException {
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new FileReader(new File(
				workingDirectory, "client_secrets.json")));

		File dataStoreFile = new File(workingDirectory, "youtube-api-uploadvideo.json");
		FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(dataStoreFile);
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
				JSON_FACTORY, clientSecrets, scopes).setDataStoreFactory(dataStoreFactory)
				.setAccessType("offline").build();
		LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(LOCAL_RECEIVER_PORT)
				.build();

		return new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");
	}

	@Override
	public void uploadVideo(String videoFilename) {
		File videoFile = new File(workingDirectory, videoFilename);
		List<String> scopes = Arrays.asList(YouTubeScopes.YOUTUBE_UPLOAD, YouTubeScopes.YOUTUBEPARTNER,
				YouTubeScopes.YOUTUBE);

		try {
			Credential credential = authorize(scopes);
			YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
					.setApplicationName("andrena-kicker").build();

			Video videoObjectDefiningMetadata = new Video();

			VideoStatus status = new VideoStatus();
			status.setPrivacyStatus("public");
			videoObjectDefiningMetadata.setStatus(status);

			VideoSnippet snippet = new VideoSnippet();
			Calendar cal = Calendar.getInstance();
			snippet.setTitle("Goal " + cal.getTime());
			videoObjectDefiningMetadata.setSnippet(snippet);

			InputStreamContent mediaContent = new InputStreamContent(VIDEO_FILE_FORMAT,
					new BufferedInputStream(new FileInputStream(videoFile)));
			mediaContent.setLength(videoFile.length());

			YouTube.Videos.Insert videoInsert = youtube.videos().insert("snippet,status,statistics",
					videoObjectDefiningMetadata, mediaContent);
			MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();

			MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
				@Override
				public void progressChanged(MediaHttpUploader uploader) throws IOException {
					switch (uploader.getUploadState()) {
					case INITIATION_STARTED:
						System.out.println("Initiation Started");
						break;
					case INITIATION_COMPLETE:
						System.out.println("Initiation Completed");
						break;
					case MEDIA_IN_PROGRESS:
						System.out.println("Upload in progress");
						System.out.println("Upload percentage: " + uploader.getProgress());
						break;
					case MEDIA_COMPLETE:
						System.out.println("Upload Completed!");
						break;
					case NOT_STARTED:
						System.out.println("Upload Not Started!");
						break;
					}
				}
			};
			uploader.setProgressListener(progressListener);

			System.out.println(videoInsert.toString());
			Video returnedVideo = videoInsert.execute();

			System.out.println("\n================== Returned Video ==================\n");
			System.out.println("  - Id: " + returnedVideo.getId());
			System.out.println("  - Title: " + returnedVideo.getSnippet().getTitle());
			System.out.println("  - Tags: " + returnedVideo.getSnippet().getTags());
			System.out.println("  - Privacy Status: " + returnedVideo.getStatus().getPrivacyStatus());
			System.out.println("  - Video Count: " + returnedVideo.getStatistics().getViewCount());

		} catch (GoogleJsonResponseException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
			e.printStackTrace();
		} catch (Throwable t) {
			System.err.println("Throwable: " + t.getMessage());
			t.printStackTrace();
		}
	}
}
