package de.andrena.kickercam.goal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.andrena.kickercam.command.CommandException;
import de.andrena.kickercam.command.CommandFactory;

public class PlaybackQueue extends Queue<String> {
	static final Logger LOGGER = LogManager.getLogger(PlaybackQueue.class);

	private CommandFactory playCommand;

	private final UploadQueue uploadQueue;

	public PlaybackQueue(CommandFactory playCommand, UploadQueue uploadQueue) {
		this.playCommand = playCommand;
		this.uploadQueue = uploadQueue;
	}

	@Override
	protected void execute(String videoFilename) {
		try {
			LOGGER.trace("Playing video: {}", videoFilename);
			playCommand.run(videoFilename).waitFor();
			LOGGER.trace("Finished playing video: {}", videoFilename);
			uploadQueue.queue(videoFilename);

		} catch (InterruptedException | CommandException e) {
			LOGGER.error("Playback failed.", e);
		}
	}

}
