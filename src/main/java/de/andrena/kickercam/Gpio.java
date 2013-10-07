package de.andrena.kickercam;

public interface Gpio {

	void enablePin3();

	void listenOnPin0(final Runnable action);

}