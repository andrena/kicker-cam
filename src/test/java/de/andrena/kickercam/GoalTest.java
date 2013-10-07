package de.andrena.kickercam;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class GoalTest {

	@Rule
	public TestEnvironment environment = new TestEnvironment();

	private Goal goal;

	@Before
	public void setUp() {
		goal = new Goal(environment);
		goal.fire();
	}

	@Test
	public void fireTrigger_RunsCommands() throws Exception {
		assertThat(environment.getCatCommandFactory().hasRun(), is(true));
		assertThat(environment.getPlayCommand().hasRun(), is(true));
		assertThat(environment.getRmCommand().hasRun(), is(true));
	}
}
