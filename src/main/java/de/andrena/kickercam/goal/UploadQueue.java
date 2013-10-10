package de.andrena.kickercam.goal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.andrena.kickercam.command.CommandException;
import de.andrena.kickercam.command.CommandFactory;

public class UploadQueue extends Queue<String> {
	static final Logger LOGGER = LogManager.getLogger(PlaybackQueue.class);

	private CommandFactory rmCommand;
	private VideoUploader videoUploader;

	public UploadQueue(CommandFactory rmCommand, VideoUploader videoUploader) {
		this.rmCommand = rmCommand;
		this.videoUploader = videoUploader;
	}

	@Override
	protected void execute(String videoFilename) {
		try {
			LOGGER.trace("Uploading video: {}", videoFilename);
			videoUploader.uploadVideo(videoFilename);
			LOGGER.trace("Finished uploading video: {}", videoFilename);
			rmCommand.run(videoFilename);
		} catch (CommandException e) {
			LOGGER.error("Uploading failed.", e);
		}
	}
}
