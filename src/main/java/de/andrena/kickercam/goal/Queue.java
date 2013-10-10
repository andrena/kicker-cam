package de.andrena.kickercam.goal;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Queue<T> {
	static final Logger LOGGER = LogManager.getLogger(Queue.class);

	private boolean running;
	private List<T> queue = new LinkedList<T>();

	public synchronized void queue(final T parameter) {
		if (!running) {
			startNewThread(parameter);
		} else {
			queue.add(parameter);
		}
	}

	private void startNewThread(final T parameter) {
		LOGGER.info("Starting new {} queue.", getClass().getSimpleName());
		running = true;
		new Thread() {
			@Override
			public void run() {
				Queue.this.run(parameter);
			}
		}.start();
	}

	private void run(T parameter) {
		execute(parameter);
		T nextParameter;
		while ((nextParameter = checkQueueForNextElement()) != null) {
			execute(nextParameter);
		}
	}

	protected abstract void execute(T parameter);

	private synchronized T checkQueueForNextElement() {
		if (!queue.isEmpty()) {
			LOGGER.info("Retrieving next element from queue.");
			return queue.remove(0);
		}
		LOGGER.info("Stopping Playback queue.");
		running = false;
		return null;
	}

	public boolean isRunning() {
		return running;
	}

}