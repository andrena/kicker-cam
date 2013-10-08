package de.andrena.kickercam;

import org.junit.Rule;
import org.junit.Test;

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
