package de.andrena.kickercam;

import java.io.InputStream;
import java.io.OutputStream;

public class ProcessMock extends Process {

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
		return 0;
	}

}
