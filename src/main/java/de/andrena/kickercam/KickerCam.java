package de.andrena.kickercam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.andrena.kickercam.command.Command;
import de.andrena.kickercam.command.CommandException;

public class KickerCam {
	private static final Logger LOGGER = LogManager.getLogger(KickerCam.class);

	private final Gpio gpio;
	private final Command recordCommand;
	private final GoalFactory goalFactory;

	public KickerCam(Environment environment) {
		goalFactory = new GoalFactory(environment);
		gpio = environment.getGpio();
		recordCommand = environment.getRecordCommand();
	}

	public void start() {
		startRecording();
		setupPins();
	}

	private void setupPins() {
		gpio.enablePin3();
		gpio.listenOnPin0(goalFactory);
	}

	private void startRecording() {
		try {
			recordCommand.run();
		} catch (CommandException e) {
			LOGGER.fatal(e.getMessage(), e);
			throw new RuntimeException("Startup failed, shutting down.", e);
		}
	}

	public static void main(String args[]) throws InterruptedException {
		LOGGER.info("KickerCam startup...");
		new KickerCam(new RaspberryPiEnvironment()).start();
		LOGGER.info("Startup successful.");

		while (true) {
			Thread.sleep(500);
		}
	}

}
