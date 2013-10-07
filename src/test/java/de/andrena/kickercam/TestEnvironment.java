package de.andrena.kickercam;

import java.io.File;

import org.junit.rules.ExternalResource;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TestEnvironment extends ExternalResource implements Environment {

	private CommandMock recordCommandMock = new CommandMock();
	private CommandMock playCommandMock = new CommandMock();
	private CommandMock rmCommandMock = new CommandMock();
	private CatCommandFactoryMock catCommandFactoryMock = new CatCommandFactoryMock();
	private GpioMock gpioMock = new GpioMock();
	private File playlistFile;

	private TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Override
	public Statement apply(Statement base, Description description) {
		return temporaryFolder.apply(super.apply(base, description), description);
	}

	@Override
	protected void before() throws Throwable {
		playlistFile = temporaryFolder.newFile();
	}

	@Override
	public CommandMock getRecordCommand() {
		return recordCommandMock;
	}

	@Override
	public CommandMock getPlayCommand() {
		return playCommandMock;
	}

	@Override
	public CommandMock getRmCommand() {
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

}
