package de.andrena.kickercam.command;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import org.junit.Test;

public class ProcessRunnerWithTimeoutTest {

	private static final int PROCESS_FULL_SECONDS = 1;
	private static final long LARGE_TIMEOUT_MS = 10000;

	@Test
	public void run_WithLargerTimeout_CompletesBelowTimeout() throws Exception {
		ProcessRunnerWithTimeout<Integer> processRunner = new ProcessRunnerWithTimeout<>(
				new SleepCommandFactory());
		long startTime = System.currentTimeMillis();
		processRunner.run(1, LARGE_TIMEOUT_MS);
		assertThat(System.currentTimeMillis() - startTime, is(lessThan(LARGE_TIMEOUT_MS)));
	}

	@Test
	public void run_WithSmallerTimeout_CompletesBelowProcessTime() throws Exception {
		ProcessRunnerWithTimeout<Integer> processRunner = new ProcessRunnerWithTimeout<>(
				new SleepCommandFactory());
		long startTime = System.currentTimeMillis();
		processRunner.run(PROCESS_FULL_SECONDS, 50);
		assertThat(System.currentTimeMillis() - startTime, is(lessThan(PROCESS_FULL_SECONDS * 1000L)));
	}
}
