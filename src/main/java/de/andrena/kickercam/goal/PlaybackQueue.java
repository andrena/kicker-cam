package de.andrena.kickercam.goal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.andrena.kickercam.command.CommandException;
import de.andrena.kickercam.command.CommandFactory;
import de.andrena.kickercam.command.ProcessRunnerWithTimeout;

public class PlaybackQueue extends Queue<GoalId> {
	private static final int PLAYBACK_TIMEOUT = 12000;

	static final Logger LOGGER = LogManager.getLogger(PlaybackQueue.class);

	private ProcessRunnerWithTimeout<GoalId> playCommandRunner;
	private final UploadQueue uploadQueue;
	private final File workingDirectory;

	public PlaybackQueue(CommandFactory<GoalId> playCommand, UploadQueue uploadQueue, File workingDirectory) {
		this.playCommandRunner = new ProcessRunnerWithTimeout<>(playCommand);
		this.uploadQueue = uploadQueue;
		this.workingDirectory = workingDirectory;
	}

	@Override
	protected void execute(GoalId goalId) {
		try {
			File subtitleFile = createSubtitleFile(goalId);
			playVideo(goalId);
			subtitleFile.delete();
			uploadQueue.queue(goalId);
		} catch (Exception e) {
			LOGGER.error("Playback failed.", e);
		}
	}

	private void playVideo(GoalId goalId) throws InterruptedException, CommandException {
		LOGGER.info("Playing video: {}", goalId.getFilename());
		playCommandRunner.run(goalId, PLAYBACK_TIMEOUT);
		LOGGER.info("Finished playing video: {}", goalId.getFilename());
	}

	private File createSubtitleFile(GoalId goalId) throws IOException {
		File subtitleFile = new File(workingDirectory, goalId.getSubtitleFilename());
		subtitleFile.createNewFile();
		FileUtils.writeLines(subtitleFile, createSubtitleInfo(goalId));
		return subtitleFile;
	}

	private Collection<?> createSubtitleInfo(GoalId goalId) {
		List<String> subtitleInfo = new ArrayList<>();
		subtitleInfo.add("1");
		subtitleInfo.add("00:00:00,000 --> 00:00:04,000");
		subtitleInfo.add("#" + goalId.getId());
		return subtitleInfo;
	}
}
