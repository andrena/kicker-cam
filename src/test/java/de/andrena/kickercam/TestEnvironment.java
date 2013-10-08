package de.andrena.kickercam;

import java.io.File;

import org.junit.rules.ExternalResource;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TestEnvironment extends ExternalResource implements Environment {

	private CommandMock recordCommandMock = new CommandMock();
	private CommandFactoryMock playCommandMock = new CommandFactoryMock();
	private CommandFactoryMock rmCommandMock = new CommandFactoryMock();
	private CatCommandFactoryMock catCommandFactoryMock = new CatCommandFactoryMock();
	private GpioMock gpioMock = new GpioMock();
	private File playlistFile;

	private TemporaryFolder temporaryFolder = new TemporaryFolder();
	private PlaybackQueue playbackQueue;

	@Override
	public Statement apply(Statement base, Description description) {
		return temporaryFolder.apply(super.apply(base, description), description);
	}

	@Override
	protected void before() throws Throwable {
		playlistFile = temporaryFolder.newFile();
		playbackQueue = new PlaybackQueue(this);
	}

	@Override
	public CommandMock getRecordCommand() {
		return recordCommandMock;
	}

	@Override
	public CommandFactoryMock getPlayCommand() {
		return playCommandMock;
	}

	@Override
	public CommandFactoryMock getRmCommand() {
		return rmCommandMock;
	}

	@Override
	public CatCommandFactoryMock getCatCommandFactory() {
		return catCommandFactoryMock;
	}

	@Override
	public GpioMock getGpio() {
		return gpioMock;
	}

	@Override
	public File getPlaylistFile() {
		return playlistFile;
	}

	@Override
	public PlaybackQueue getPlaybackQueue() {
		return playbackQueue;
	}

	public void waitForQueueToFinish() {
		System.out.println("waiting on " + playbackQueue);
		while (playbackQueue.isRunning()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
