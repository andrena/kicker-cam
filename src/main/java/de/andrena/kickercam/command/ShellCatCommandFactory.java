package de.andrena.kickercam.command;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ShellCatCommandFactory implements CatCommandFactory {
	private final File workingDirectory;

	public ShellCatCommandFactory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	@Override
	public Process run(List<String> playlistFiles, String mergedVideoFilename) throws CommandException {
		return new ShellCommand("MP4Box -cat " + StringUtils.join(playlistFiles, " -cat ") + " "
				+ mergedVideoFilename, workingDirectory).run();
	}

}
