package de.andrena.kickercam.mock;

import de.andrena.kickercam.command.CommandException;
import de.andrena.kickercam.command.CommandFactory;

public class CommandFactoryMock implements CommandFactory {

	private boolean hasRun;
	private int delayMillis;

	@Override
	public Process run(String parameter) throws CommandException {
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
