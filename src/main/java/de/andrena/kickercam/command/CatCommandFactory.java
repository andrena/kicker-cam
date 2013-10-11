package de.andrena.kickercam.command;

import java.io.File;

import org.apache.commons.lang.StringUtils;

public class CatCommandFactory implements CommandFactory<CatParameter> {
	private final File workingDirectory;

	public CatCommandFactory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	@Override
	public Process run(CatParameter parameter) throws CommandException {
		return new ShellCommand("MP4Box -cat " + StringUtils.join(parameter.getPlaylistFiles(), " -cat ")
				+ " " + parameter.getMergedVideoFilename(), workingDirectory).run();
	}

}
