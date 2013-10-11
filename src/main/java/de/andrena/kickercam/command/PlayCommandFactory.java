package de.andrena.kickercam.command;

import java.io.File;

import de.andrena.kickercam.goal.GoalId;

public class PlayCommandFactory implements CommandFactory<GoalId> {

	private final File workingDirectory;

	public PlayCommandFactory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	@Override
	public Process run(GoalId goalId) throws CommandException {
		return new ShellCommand("omxplayer --subtitle " + goalId.getSubtitleFilename() + " "
				+ goalId.getFilename(), workingDirectory).run();
	}
}
