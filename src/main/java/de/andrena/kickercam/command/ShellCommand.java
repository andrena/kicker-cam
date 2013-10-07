package de.andrena.kickercam.command;

import java.io.File;
import java.io.IOException;

public class ShellCommand implements Command {
	private ProcessBuilder processBuilder;
	private final String command;
	private final File workingDirectory;

	public ShellCommand(String command, File workingDirectory) {
		this.command = command;
		this.workingDirectory = workingDirectory;
		processBuilder = new ProcessBuilder("/bin/sh", "-c", command).inheritIO().directory(workingDirectory);
	}

	@Override
	public Process run() throws CommandException {
		try {
			return processBuilder.start();
		} catch (IOException e) {
			throw new CommandException("Error running " + toString() + ".", e);
		}
	}

	@Override
	public String toString() {
		return "command '" + command + "' in working directory '" + workingDirectory + "'";
	}

}
