package de.andrena.kickercam.command;

public interface Command {
	Process run() throws CommandException;
}
