package de.andrena.kickercam.command;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProcessRunnerWithTimeout<T> {
	private static final Logger LOGGER = LogManager.getLogger(ProcessRunnerWithTimeout.class);

	private final class ProcessDestroyingTask extends TimerTask {
		private final Process playProcess;
		private final T parameter;

		private ProcessDestroyingTask(Process playProcess, T parameter) {
			this.playProcess = playProcess;
			this.parameter = parameter;
		}

		@Override
		public void run() {
			LOGGER.warn("Killing process from command {} with parameter {}", command.getClass()
					.getSimpleName(), parameter);
			playProcess.destroy();
		}
	}

	private final CommandFactory<T> command;

	public ProcessRunnerWithTimeout(CommandFactory<T> command) {
		this.command = command;
	}

	public void run(T parameter, long timeoutInMs) throws CommandException, InterruptedException {
		Timer timeoutTimer = new Timer("ProcessTimeoutTimer");
		final Process playProcess = command.run(parameter);
		timeoutTimer.schedule(new ProcessDestroyingTask(playProcess, parameter), timeoutInMs);
		playProcess.waitFor();
		timeoutTimer.cancel();
	}
}
