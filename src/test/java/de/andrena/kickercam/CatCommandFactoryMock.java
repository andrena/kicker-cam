package de.andrena.kickercam;

import java.util.List;

import de.andrena.kickercam.command.CatCommandFactory;
import de.andrena.kickercam.command.CommandException;

public class CatCommandFactoryMock implements CatCommandFactory {

	private boolean hasRun;
	private List<String> lastPlaylistFiles;

	@Override
	public Process run(List<String> playlistFiles, String mergedVideoFilename) throws CommandException {
		hasRun = true;
		lastPlaylistFiles = playlistFiles;
		return new ProcessMock();
	}

	public boolean hasRun() {
		return hasRun;
	}

	public List<String> getLastPlaylistFiles() {
		return lastPlaylistFiles;
	}

}
