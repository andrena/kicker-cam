package de.andrena.kickercam;

public class GpioMock implements Gpio {
	private boolean pin3Enabled;
	private Runnable listenerAction;

	@Override
	public void enablePin3() {
		pin3Enabled = true;
	}

	public boolean isPin3Enabled() {
		return pin3Enabled;
	}

	@Override
	public void listenOnPin0(Runnable action) {
		listenerAction = action;
	}

	public Runnable getListenerAction() {
		return listenerAction;
	}
}
