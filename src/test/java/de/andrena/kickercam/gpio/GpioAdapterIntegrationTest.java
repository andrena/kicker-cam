package de.andrena.kickercam.gpio;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GpioAdapterIntegrationTest {
	@Test
	public void enablePin3_SetsHighState() throws Exception {
		GpioAdapter gpio = new GpioAdapter();
		gpio.enablePin3();
		assertThat(gpio.getPin3State(), is(true));
	}
}
