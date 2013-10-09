package de.andrena.kickercam.goal;

import de.andrena.kickercam.Environment;

public class GoalFactory implements Runnable {
	private static final int DEFAULT_DISREGARD_MILLIS = 1000;
	private final Environment environment;
	private final int disregardMillis;
	private long lastRun;

	public GoalFactory(Environment environment) {
		this.environment = environment;
		disregardMillis = DEFAULT_DISREGARD_MILLIS;
	}

	GoalFactory(Environment environment, int disregardMillis) {
		this.environment = environment;
		this.disregardMillis = disregardMillis;
	}

	@Override
	public void run() {
		long now = System.currentTimeMillis();
		if (now - lastRun >= disregardMillis) {
			lastRun = now;
			new Goal(environment).fire();
		}
	}

}
