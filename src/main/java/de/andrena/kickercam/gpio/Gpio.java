package de.andrena.kickercam.gpio;

public interface Gpio {

	void enablePin3();

	void listenOnPin0(final Runnable action);

}