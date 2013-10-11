package de.andrena.kickercam;

import java.io.File;

import de.andrena.kickercam.command.CatParameter;
import de.andrena.kickercam.command.Command;
import de.andrena.kickercam.command.CommandFactory;
import de.andrena.kickercam.goal.GoalId;
import de.andrena.kickercam.goal.PlaybackQueue;
import de.andrena.kickercam.goal.UploadQueue;
import de.andrena.kickercam.gpio.Gpio;

public interface Environment {

	Command getRecordCommand();

	CommandFactory<GoalId> getPlayCommand();

	CommandFactory<GoalId> getRmCommand();

	CommandFactory<CatParameter> getCatCommandFactory();

	Gpio getGpio();

	File getPlaylistFile();

	PlaybackQueue getPlaybackQueue();

	UploadQueue getUploadQueue();

	Database getDatabase();
}