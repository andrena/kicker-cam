package de.andrena.kickercam;

import java.io.File;

import de.andrena.kickercam.command.CatCommandFactory;
import de.andrena.kickercam.command.Command;
import de.andrena.kickercam.command.CommandFactory;

public interface Environment {

	Command getRecordCommand();

	CommandFactory getPlayCommand();

	CommandFactory getRmCommand();

	CatCommandFactory getCatCommandFactory();

	Gpio getGpio();

	File getPlaylistFile();

	PlaybackQueue getPlaybackQueue();

}