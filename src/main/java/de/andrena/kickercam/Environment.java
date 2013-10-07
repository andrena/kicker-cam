package de.andrena.kickercam;

import java.io.File;

import de.andrena.kickercam.command.CatCommandFactory;
import de.andrena.kickercam.command.Command;

public interface Environment {

	Command getRecordCommand();

	Command getPlayCommand();

	Command getRmCommand();

	CatCommandFactory getCatCommandFactory();

	Gpio getGpio();

	File getPlaylistFile();

}