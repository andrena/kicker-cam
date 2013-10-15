package de.andrena.kickercam.command;

import java.io.IOException;

public class SleepCommandFactory implements CommandFactory<Integer> {

	@Override
	public Process run(final Integer seconds) throws CommandException {
		return new Command() {

			@Override
			public Process run() throws CommandException {
				try {
					return new ProcessBuilder("sleep", seconds.toString()).inheritIO().start();
				} catch (IOException e) {
					throw new CommandException("Cannot run sleep command.", e);
				}
			}
		}.run();
	}

}
