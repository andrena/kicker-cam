package de.andrena.kickercam;

import java.io.File;

import de.andrena.kickercam.command.CatCommandFactory;
import de.andrena.kickercam.command.Command;
import de.andrena.kickercam.command.CommandFactory;
import de.andrena.kickercam.command.PlayCommandFactory;
import de.andrena.kickercam.command.RecordCommand;
import de.andrena.kickercam.command.RmCommandFactory;
import de.andrena.kickercam.command.ShellCatCommandFactory;
import de.andrena.kickercam.goal.PlaybackQueue;
import de.andrena.kickercam.gpio.Gpio;
import de.andrena.kickercam.gpio.GpioAdapter;

public class RaspberryPiEnvironment implements Environment {
	private static final File WORKING_DIRECTORY = new File("/home/balotelli/kicker");
	private static final File PLAYLIST_FILE = new File(WORKING_DIRECTORY, RecordCommand.PLAYLIST_FILENAME);

	private RecordCommand recordCommand = new RecordCommand(WORKING_DIRECTORY);
	private PlayCommandFactory playCommandFactory = new PlayCommandFactory(WORKING_DIRECTORY);
	private RmCommandFactory rmCommandFactory = new RmCommandFactory(WORKING_DIRECTORY);
	private ShellCatCommandFactory catCommandFactory = new ShellCatCommandFactory(WORKING_DIRECTORY);
	private GpioAdapter gpioAdapter = new GpioAdapter();
	private PlaybackQueue playbackQueue = new PlaybackQueue(this);

	@Override
	public Command getRecordCommand() {
		return recordCommand;
	}

	@Override
	public CommandFactory getPlayCommand() {
		return playCommandFactory;
	}

	@Override
	public CommandFactory getRmCommand() {
		return rmCommandFactory;
	}

	@Override
	public CatCommandFactory getCatCommandFactory() {
		return catCommandFactory;
	}

	@Override
	public Gpio getGpio() {
		return gpioAdapter;
	}

	@Override
	public File getPlaylistFile() {
		return PLAYLIST_FILE;
	}

	@Override
	public PlaybackQueue getPlaybackQueue() {
		return playbackQueue;
	}

}
