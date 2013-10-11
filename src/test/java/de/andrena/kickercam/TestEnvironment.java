package de.andrena.kickercam;

import java.io.File;
import java.sql.SQLException;

import org.junit.rules.ExternalResource;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import de.andrena.kickercam.command.CatParameter;
import de.andrena.kickercam.goal.GoalId;
import de.andrena.kickercam.goal.PlaybackQueue;
import de.andrena.kickercam.goal.UploadQueue;
import de.andrena.kickercam.mock.CommandFactoryMock;
import de.andrena.kickercam.mock.CommandMock;
import de.andrena.kickercam.mock.GpioMock;
import de.andrena.kickercam.mock.VideoUploaderMock;

public class TestEnvironment extends ExternalResource implements Environment {

	private CommandMock recordCommandMock = new CommandMock();
	private CommandFactoryMock<GoalId> playCommandMock = new CommandFactoryMock<>();
	private CommandFactoryMock<GoalId> rmCommandMock = new CommandFactoryMock<>();
	private CommandFactoryMock<CatParameter> catCommandFactoryMock = new CommandFactoryMock<>();
	private GpioMock gpioMock = new GpioMock();
	private File playlistFile;

	private TemporaryFolder temporaryFolderRule = new TemporaryFolder();
	private PlaybackQueue playbackQueue;
	private UploadQueue uploadQueue;
	private VideoUploaderMock videoUploaderMock;
	private File workingDirectory;
	private SqliteDatabase database;

	@Override
	public Statement apply(Statement base, Description description) {
		return temporaryFolderRule.apply(super.apply(base, description), description);
	}

	@Override
	protected void before() throws Throwable {
		workingDirectory = temporaryFolderRule.newFolder();
		playlistFile = new File(workingDirectory, "playlist.file");
		playlistFile.createNewFile();
		videoUploaderMock = new VideoUploaderMock();
		uploadQueue = new UploadQueue(rmCommandMock, videoUploaderMock);
		playbackQueue = new PlaybackQueue(playCommandMock, uploadQueue, workingDirectory);
		database = new SqliteDatabase(workingDirectory);
		initializeDatabase(database);
	}

	@Override
	public CommandMock getRecordCommand() {
		return recordCommandMock;
	}

	@Override
	public CommandFactoryMock<GoalId> getPlayCommand() {
		return playCommandMock;
	}

	@Override
	public CommandFactoryMock<GoalId> getRmCommand() {
		return rmCommandMock;
	}

	@Override
	public CommandFactoryMock<CatParameter> getCatCommandFactory() {
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
		while (playbackQueue.isRunning()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public UploadQueue getUploadQueue() {
		return uploadQueue;
	}

	@Override
	public Database getDatabase() {
		return database;
	}

	public File getWorkingDirectory() {
		return workingDirectory;
	}

	private void initializeDatabase(SqliteDatabase database) {
		try {
			database.createStatement().execute(
					"CREATE TABLE goal (id INTEGER PRIMARY KEY AUTOINCREMENT, scored DATETIME NOT NULL);");
		} catch (SQLException e) {
			throw new RuntimeException("Error creating test database.", e);
		}
	}
}
