package de.andrena.kickercam.command;

import java.io.File;

public class PlayCommandFactory implements CommandFactory {

	private final File workingDirectory;

	public PlayCommandFactory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	@Override
	public Process run(String mergedVideoFilename) throws CommandException {
		return new ShellCommand("omxplayer " + mergedVideoFilename, workingDirectory).run();
	}

}
