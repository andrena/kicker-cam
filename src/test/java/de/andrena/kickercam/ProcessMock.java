package de.andrena.kickercam;

import java.io.InputStream;
import java.io.OutputStream;

public class ProcessMock extends Process {

	private final int delayMillis;

	public ProcessMock() {
		delayMillis = 0;
	}

	public ProcessMock(int delayMillis) {
		this.delayMillis = delayMillis;
	}

	@Override
	public void destroy() {
	}

	@Override
	public int exitValue() {
		return 0;
	}

	@Override
	public InputStream getErrorStream() {
		return null;
	}

	@Override
	public InputStream getInputStream() {
		return null;
	}

	@Override
	public OutputStream getOutputStream() {
		return null;
	}

	@Override
	public int waitFor() throws InterruptedException {
		if (delayMillis > 0) {
			Thread.sleep(delayMillis);
		}
		return 0;
	}

}
