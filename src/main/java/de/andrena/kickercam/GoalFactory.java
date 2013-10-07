package de.andrena.kickercam;

public class GoalFactory implements Runnable {
	private final Environment environment;

	public GoalFactory(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void run() {
		new Goal(environment).fire();
	}

}
