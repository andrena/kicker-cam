package de.andrena.kickercam.command;

import java.io.File;

public class RmCommand extends ShellCommand {

	public RmCommand(String lastSceneFilename, File workingDirectory) {
		super("rm -f " + lastSceneFilename, workingDirectory);
	}

}
