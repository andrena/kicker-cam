package de.andrena.kickercam.command;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ShellCatCommandFactory implements CatCommandFactory {
	private String lastSceneFilename;
	private final File workingDirectory;

	public ShellCatCommandFactory(String lastSceneFilename, File workingDirectory) {
		this.lastSceneFilename = lastSceneFilename;
		this.workingDirectory = workingDirectory;
	}

	@Override
	public Process run(List<String> playlistFiles) throws CommandException {
		return new ShellCommand("MP4Box -cat " + StringUtils.join(playlistFiles, " -cat ") + " "
				+ lastSceneFilename, workingDirectory).run();
	}

}
