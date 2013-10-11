package de.andrena.kickercam.command;

import java.io.File;

import de.andrena.kickercam.goal.GoalId;

public class RmCommandFactory implements CommandFactory<GoalId> {

	private final File workingDirectory;

	public RmCommandFactory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	@Override
	public Process run(GoalId goalId) throws CommandException {
		return new ShellCommand("rm -f " + goalId.getFilename(), workingDirectory).run();
	}

}
