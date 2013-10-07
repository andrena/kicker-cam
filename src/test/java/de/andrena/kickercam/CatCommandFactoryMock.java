package de.andrena.kickercam;

import java.util.List;

import de.andrena.kickercam.command.CatCommandFactory;
import de.andrena.kickercam.command.CommandException;

public class CatCommandFactoryMock implements CatCommandFactory {

	private boolean hasRun;

	@Override
	public Process run(List<String> playlistFiles) throws CommandException {
		hasRun = true;
		return new ProcessMock();
	}

	public boolean hasRun() {
		return hasRun;
	}

}
