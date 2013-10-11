package de.andrena.kickercam.mock;

import de.andrena.kickercam.command.CommandException;
import de.andrena.kickercam.command.CommandFactory;
import de.andrena.kickercam.goal.TestRunnable;

public class CommandFactoryMock<T> implements CommandFactory<T> {

	private boolean hasRun;
	private int delayMillis;
	private T lastParameter;
	private TestRunnable listener;

	@Override
	public Process run(T parameter) throws CommandException {
		lastParameter = parameter;
		hasRun = true;
		runListener();
		return new ProcessMock(delayMillis);
	}

	private void runListener() {
		if (listener != null) {
			try {
				listener.run();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public boolean hasRun() {
		return hasRun;
	}

	public void setDelay(int delayMillis) {
		this.delayMillis = delayMillis;
	}

	public void resetHasRun() {
		hasRun = false;
	}

	public T getLastParameter() {
		return lastParameter;
	}

	public void setListener(TestRunnable listener) {
		this.listener = listener;
	}

}
