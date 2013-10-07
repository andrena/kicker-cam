package de.andrena.kickercam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.andrena.kickercam.command.CatCommandFactory;
import de.andrena.kickercam.command.Command;

public class Goal {
	private static Logger LOGGER = LogManager.getLogger(Goal.class);

	private final CatCommandFactory catCommand;
	private final Command playCommand;
	private final Command rmCommand;
	private final File playlistFile;

	public Goal(Environment environment) {
		catCommand = environment.getCatCommandFactory();
		playCommand = environment.getPlayCommand();
		rmCommand = environment.getRmCommand();
		playlistFile = environment.getPlaylistFile();
	}

	public void fire() {
		try {
			fireTriggerUnsafe();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException("Goal failed.", e);
		}
	}

	private void fireTriggerUnsafe() throws Exception {
		LOGGER.info("Goal scored.");
		catCommand.run(getPlaylistFiles()).waitFor();
		playCommand.run().waitFor();
		rmCommand.run().waitFor();
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
