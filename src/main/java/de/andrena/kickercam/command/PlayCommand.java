package de.andrena.kickercam.command;

import java.io.File;

public class PlayCommand extends ShellCommand {

	public PlayCommand(String lastSceneFilename, File workingDirectory) {
		super("omxplayer " + lastSceneFilename, workingDirectory);
	}

}
