package de.andrena.kickercam.gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class GpioAdapter implements Gpio {
	private GpioController gpio = GpioFactory.getInstance();
	private GpioPinDigitalOutput inputPin3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03);

	@Override
	public void enablePin3() {
		gpio.setState(true, inputPin3);
	}

	public boolean getPin3State() {
		return gpio.getState(inputPin3).isHigh();
	}

	@Override
	public void listenOnPin0(final Runnable action) {
		GpioPinDigitalInput trigger = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00,
				PinPullResistance.PULL_DOWN);

		trigger.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				if (event.getState() == PinState.HIGH) {
					action.run();
				}
			}
		});
	}
}
