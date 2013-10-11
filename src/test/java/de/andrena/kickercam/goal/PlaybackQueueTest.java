package de.andrena.kickercam.goal;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;

import de.andrena.kickercam.TestEnvironment;

public class PlaybackQueueTest {
	@Rule
	public TestEnvironment environment = new TestEnvironment();

	@Test
	public void queueTwo() throws Exception {
		PlaybackQueue queue = new PlaybackQueue(environment.getPlayCommand(), environment.getUploadQueue());
		queue.queue(new GoalId(new Date(), 1));
		queue.queue(new GoalId(new Date(), 2));
	}
}
