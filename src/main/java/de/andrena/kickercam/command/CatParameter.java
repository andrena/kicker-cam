package de.andrena.kickercam.command;

import java.util.List;

public class CatParameter {
	private final List<String> playlistFiles;
	private final String mergedVideoFilename;

	public CatParameter(List<String> playlistFiles, String mergedVideoFilename) {
		this.playlistFiles = playlistFiles;
		this.mergedVideoFilename = mergedVideoFilename;
	}

	public String getMergedVideoFilename() {
		return mergedVideoFilename;
	}

	public List<String> getPlaylistFiles() {
		return playlistFiles;
	}
}