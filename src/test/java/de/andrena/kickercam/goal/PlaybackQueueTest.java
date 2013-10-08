package de.andrena.kickercam.goal;

import org.junit.Rule;
import org.junit.Test;

import de.andrena.kickercam.TestEnvironment;

import de.andrena.kickercam.goal.PlaybackQueue;

public class PlaybackQueueTest {
	@Rule
	public TestEnvironment environment = new TestEnvironment();

	@Test
	public void queueTwo() throws Exception {
		PlaybackQueue queue = new PlaybackQueue(environment);
		queue.queue("1");
		queue.queue("2");
	}
}
