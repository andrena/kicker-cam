package de.andrena.kickercam.command;

public class CommandException extends Exception {

	private static final long serialVersionUID = 5868906043915406739L;

	public CommandException(String message, Throwable cause) {
		super(message, cause);
	}
}
