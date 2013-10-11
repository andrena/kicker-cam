package de.andrena.kickercam.goal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.andrena.kickercam.command.CommandException;
import de.andrena.kickercam.command.CommandFactory;

public class UploadQueue extends Queue<GoalId> {
	static final Logger LOGGER = LogManager.getLogger(PlaybackQueue.class);

	private CommandFactory<GoalId> rmCommand;
	private VideoUploader videoUploader;

	public UploadQueue(CommandFactory<GoalId> rmCommand, VideoUploader videoUploader) {
		this.rmCommand = rmCommand;
		this.videoUploader = videoUploader;
	}

	@Override
	protected void execute(GoalId goalId) {
		try {
			LOGGER.trace("Uploading video: {}", goalId.getFilename());
			videoUploader.uploadVideo(goalId);
			LOGGER.trace("Finished uploading video: {}", goalId.getFilename());
			rmCommand.run(goalId);
		} catch (CommandException e) {
			LOGGER.error("Uploading failed.", e);
		}
	}
}
