package de.andrena.kickercam;

import de.andrena.kickercam.command.Command;

public class CommandMock implements Command {

	private boolean hasRun;

	@Override
	public Process run() {
		hasRun = true;
		return new ProcessMock();
	}

	public boolean hasRun() {
		return hasRun;
	}

}
