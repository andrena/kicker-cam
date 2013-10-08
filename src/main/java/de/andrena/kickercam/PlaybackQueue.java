package de.andrena.kickercam;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.andrena.kickercam.command.CommandException;
import de.andrena.kickercam.command.CommandFactory;

public class PlaybackQueue {
	private static final Logger LOGGER = LogManager.getLogger(PlaybackQueue.class);

	private CommandFactory rmCommand;
	private CommandFactory playCommand;
	private boolean running;
	private List<String> queue = new LinkedList<String>();

	public PlaybackQueue(Environment environment) {
		this.playCommand = environment.getPlayCommand();
		this.rmCommand = environment.getRmCommand();
	}

	public synchronized void queue(final String videoFilename) throws Exception {
		if (!running) {
			startNewThread(videoFilename);
		} else {
			queue.add(videoFilename);
		}
	}

	private void startNewThread(final String videoFilename) {
		LOGGER.info("Starting new Playback queue.");
		running = true;
		new Thread() {
			@Override
			public void run() {
				PlaybackQueue.this.run(videoFilename);
			}
		}.start();
	}

	private void run(String videoFilename) {
		play(videoFilename);
		String nextVideoFilename;
		while ((nextVideoFilename = checkQueueForNextElement()) != null) {
			play(nextVideoFilename);
		}
	}

	private synchronized String checkQueueForNextElement() {
		if (!queue.isEmpty()) {
			LOGGER.info("Retrieving next element from queue.");
			return queue.remove(0);
		}
		LOGGER.info("Stopping Playback queue.");
		running = false;
		return null;
	}

	private void play(String videoFilename) {
		try {
			LOGGER.trace("BEFORE VIDEO: {}", System.currentTimeMillis());
			LOGGER.info("command: {}", playCommand);
			Process run = playCommand.run(videoFilename);
			LOGGER.info("process: {}", run);
			run.waitFor();
			LOGGER.trace("AFTER VIDEO: {}", System.currentTimeMillis());
			rmCommand.run(videoFilename);
		} catch (InterruptedException | CommandException e) {
			LOGGER.error("Playback failed.", e);
		}
	}

	public boolean isRunning() {
		return running;
	}

}
