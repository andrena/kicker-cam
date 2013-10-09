package de.andrena.kickercam.mock;

import de.andrena.kickercam.command.Command;

public class CommandMock implements Command {

	private boolean hasRun;
	private int delayMillis;

	@Override
	public Process run() {
		hasRun = true;
		return new ProcessMock(delayMillis);
	}

	public boolean hasRun() {
		return hasRun;
	}

	public void setDelay(int delayMillis) {
		this.delayMillis = delayMillis;
	}

}
