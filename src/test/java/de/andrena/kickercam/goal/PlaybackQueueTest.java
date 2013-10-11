package de.andrena.kickercam.goal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;

import de.andrena.kickercam.TestEnvironment;

public class PlaybackQueueTest {
	@Rule
	public TestEnvironment environment = new TestEnvironment();
	private GoalId goalId;

	@Test
	public void queue_CreatesSubtitleFile() throws Exception {
		queueWithPlayCommandListener(new TestRunnable() {
			@Override
			public void run() throws Exception {
				assertThat(getGeneratedSubtitleFile().exists(), is(true));
			}
		});
	}

	@Test
	public void queue_CreatesSubtitleFile_WithSubtitleInformation() throws Exception {
		queueWithPlayCommandListener(new TestRunnable() {
			@Override
			public void run() throws Exception {
				List<String> subtitleInfo = FileUtils.readLines(getGeneratedSubtitleFile());
				assertThat(subtitleInfo, hasSize(3));
				assertThat(subtitleInfo.get(0), is("1"));
				assertThat(subtitleInfo.get(1), is("00:00:00,000 --> 00:00:04,000"));
				assertThat(subtitleInfo.get(2), is("#" + goalId.getId()));
			}
		});
	}

	private void queueWithPlayCommandListener(TestRunnable listener) {
		environment.getPlayCommand().setListener(listener);
		goalId = queueAndWaitForGoal();
	}

	private File getGeneratedSubtitleFile() {
		return new File(environment.getWorkingDirectory(), goalId.getSubtitleFilename());
	}

	private GoalId queueAndWaitForGoal() {
		GoalId goalId = new GoalId(new Date(), 1);
		environment.getPlaybackQueue().queue(goalId);
		environment.waitForQueueToFinish();
		return goalId;
	}
}
