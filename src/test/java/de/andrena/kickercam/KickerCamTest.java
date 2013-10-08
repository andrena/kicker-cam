package de.andrena.kickercam;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.andrena.kickercam.goal.GoalFactory;

public class KickerCamTest {
	@Rule
	public TestEnvironment environment = new TestEnvironment();

	private KickerCam kickerCam;

	@Before
	public void setUp() {
		kickerCam = new KickerCam(environment);
		kickerCam.start();
	}

	@Test
	public void onStart_TriggerRecording() throws Exception {
		assertThat(environment.getRecordCommand().hasRun(), is(true));
	}

	@Test
	public void onStart_EnablePin3() throws Exception {
		assertThat(environment.getGpio().isPin3Enabled(), is(true));
	}

	@Test
	public void onStart_RegisterGoalFactory() throws Exception {
		assertThat(environment.getGpio().getListenerAction(), is(instanceOf(GoalFactory.class)));
	}

}
