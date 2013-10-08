package de.andrena.kickercam.goal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.andrena.kickercam.Environment;
import de.andrena.kickercam.command.CatCommandFactory;

public class Goal {
	private static Logger LOGGER = LogManager.getLogger(Goal.class);

	private final CatCommandFactory catCommand;
	private final File playlistFile;

	private final PlaybackQueue playbackQueue;

	public Goal(Environment environment) {
		playbackQueue = environment.getPlaybackQueue();
		catCommand = environment.getCatCommandFactory();
		playlistFile = environment.getPlaylistFile();
	}

	public void fire() {
		try {
			fireTriggerUnsafe();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException("Goal playback failed.", e);
		}
	}

	private void fireTriggerUnsafe() throws Exception {
		String mergedVideoFilename = createTimestampedFilename();
		LOGGER.info("Goal {} scored.", mergedVideoFilename);
		catCommand.run(getPlaylistFiles(), mergedVideoFilename).waitFor();
		playbackQueue.queue(mergedVideoFilename);
	}

	private String createTimestampedFilename() {
		return new SimpleDateFormat("yyyyMMMdd_HHmmss-SSS").format(new Date()) + ".mp4";
	}

	private List<String> getPlaylistFiles() throws IOException, FileNotFoundException {
		List<String> playlistFiles = new ArrayList<>();
		List<String> playlistLines = IOUtils.readLines(new FileInputStream(playlistFile));
		for (String playlistLine : playlistLines) {
			if (!playlistLine.startsWith("#")) {
				playlistFiles.add(playlistLine);
			}
		}
		return playlistFiles;
	}

}
