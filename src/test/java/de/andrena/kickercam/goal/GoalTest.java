package de.andrena.kickercam.goal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.andrena.kickercam.TestEnvironment;

import de.andrena.kickercam.goal.Goal;

public class GoalTest {

	private static final String CORRECT_PLAYLIST = "correct.mp4";

	@Rule
	public TestEnvironment environment = new TestEnvironment();

	private Goal goal;

	@Before
	public void setUp() {
		goal = createGoal();
	}

	@Test
	public void fireTrigger_RunsCommands() throws Exception {
		goal.fire();
		environment.waitForQueueToFinish();
		assertThat(environment.getCatCommandFactory().hasRun(), is(true));
		assertThat(environment.getPlayCommand().hasRun(), is(true));
		assertThat(environment.getRmCommand().hasRun(), is(true));
	}

	@Test
	public void twoGoals_InShortSuccession_GetQueuedForPlayback() throws Exception {
		writePlaylistFile(CORRECT_PLAYLIST);
		writePlaylistFileWithDelay(50, "incorrect.mp4");
		environment.getPlayCommand().setDelay(100);
		goal.fire();
		createGoal().fire();
		assertThat(environment.getCatCommandFactory().getLastPlaylistFiles(), contains(CORRECT_PLAYLIST));
	}

	private Goal createGoal() {
		return new Goal(environment);
	}

	private void writePlaylistFileWithDelay(final int delayMillis, final String playlist) {
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(delayMillis);
					writePlaylistFile(playlist);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}.start();
	}

	private void writePlaylistFile(String playlist) throws IOException {
		FileUtils.write(environment.getPlaylistFile(), playlist);
	}
}
