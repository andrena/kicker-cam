package de.andrena.kickercam;

import java.io.File;

import de.andrena.kickercam.command.CatCommandFactory;
import de.andrena.kickercam.command.Command;
import de.andrena.kickercam.command.PlayCommand;
import de.andrena.kickercam.command.RecordCommand;
import de.andrena.kickercam.command.RmCommand;
import de.andrena.kickercam.command.ShellCatCommandFactory;

public class RaspberryPiEnvironment implements Environment {
	private static final File WORKING_DIRECTORY = new File("/home/balotelli/kicker");
	private static final String LAST_SCENE_FILENAME = "last-scene.mp4";
	private static final File PLAYLIST_FILE = new File(WORKING_DIRECTORY, RecordCommand.PLAYLIST_FILENAME);

	@Override
	public Command getRecordCommand() {
		return new RecordCommand(WORKING_DIRECTORY);
	}

	@Override
	public Command getPlayCommand() {
		return new PlayCommand(LAST_SCENE_FILENAME, WORKING_DIRECTORY);
	}

	@Override
	public Command getRmCommand() {
		return new RmCommand(LAST_SCENE_FILENAME, WORKING_DIRECTORY);
	}

	@Override
	public CatCommandFactory getCatCommandFactory() {
		return new ShellCatCommandFactory(LAST_SCENE_FILENAME, WORKING_DIRECTORY);
	}

	@Override
	public Gpio getGpio() {
		return new GpioAdapter();
	}

	@Override
	public File getPlaylistFile() {
		return PLAYLIST_FILE;
	}
}
