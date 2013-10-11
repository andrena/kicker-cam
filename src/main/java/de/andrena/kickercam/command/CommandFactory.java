package de.andrena.kickercam.command;

public interface CommandFactory<T> {
	Process run(T parameter) throws CommandException;
}
