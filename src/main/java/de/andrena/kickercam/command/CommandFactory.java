package de.andrena.kickercam.command;


public interface CommandFactory {
	Process run(String parameter) throws CommandException;
}
