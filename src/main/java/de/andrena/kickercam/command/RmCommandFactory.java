package de.andrena.kickercam.command;

import java.io.File;

public class RmCommandFactory implements CommandFactory {

	private final File workingDirectory;

	public RmCommandFactory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	@Override
	public Process run(String mergedVideoFilename) throws CommandException {
		return new ShellCommand("rm -f " + mergedVideoFilename, workingDirectory).run();
	}

}
