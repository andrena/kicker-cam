package de.andrena.kickercam.goal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.andrena.kickercam.TestEnvironment;

public class GoalFactoryTest {

	private static final int DISREGARD_FRAME = 200;

	@Rule
	public TestEnvironment environment = new TestEnvironment();

	private GoalFactory goalFactory;

	@Before
	public void setUp() {
		goalFactory = new GoalFactory(environment, DISREGARD_FRAME);
	}

	@Test
	public void twoTriggersInShortSuccession_DontTriggerTwoGoals() throws Exception {
		goalFactory.run();
		environment.getCatCommandFactory().resetHasRun();
		goalFactory.run();
		assertThat(environment.getCatCommandFactory().hasRun(), is(false));
	}

	@Test
	public void twoTriggersWithEnoughTimeInBetween_TriggerTwoGoals() throws Exception {
		goalFactory.run();
		environment.getCatCommandFactory().resetHasRun();
		sleepAtLeast(DISREGARD_FRAME);
		goalFactory.run();
		assertThat(environment.getCatCommandFactory().hasRun(), is(true));
	}

	private void sleepAtLeast(int disregardFrame) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() <= startTime + disregardFrame) {
			Thread.sleep(10);
		}
	}
}
