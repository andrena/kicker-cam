package de.andrena.kickercam.goal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.andrena.kickercam.Database;
import de.andrena.kickercam.Environment;
import de.andrena.kickercam.command.CatCommandFactory;

public class Goal {
	private static Logger LOGGER = LogManager.getLogger(Goal.class);

	private final CatCommandFactory catCommand;
	private final File playlistFile;

	private final PlaybackQueue playbackQueue;

	private Database database;

	public Goal(Environment environment) {
		playbackQueue = environment.getPlaybackQueue();
		catCommand = environment.getCatCommandFactory();
		playlistFile = environment.getPlaylistFile();
		database = environment.getDatabase();
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
		Date goalTimestamp = new Date();
		GoalId goalId = new GoalId(goalTimestamp, createNewId(goalTimestamp));
		LOGGER.info("Goal {} scored.", goalId.getTitle());
		catCommand.run(getPlaylistFiles(), goalId.getFilename()).waitFor();
		playbackQueue.queue(goalId);
	}

	private long createNewId(Date goalTimestamp) throws Exception {
		PreparedStatement statement = database
				.createPreparedStatement("INSERT INTO goal (scored) VALUES (?);");
		statement.setDate(1, new java.sql.Date(goalTimestamp.getTime()));
		statement.executeUpdate();
		ResultSet generatedKeys = statement.getGeneratedKeys();
		generatedKeys.next();
		return generatedKeys.getInt(1);
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
