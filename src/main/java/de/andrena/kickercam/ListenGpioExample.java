package de.andrena.kickercam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class ListenGpioExample {

	private static final File WORKING_DIRECTORY = new File("/home/balotelli/kicker");
	private static final String LAST_SCENE_FILENAME = "last-scene.mp4";
	private static final File PLAYLIST_FILE = new File(WORKING_DIRECTORY, "list.m3u8");

	public static void main(String args[]) throws InterruptedException {
		System.out.println("Balotelli is ready!");
		GpioController gpio = GpioFactory.getInstance();
		enablePin3(gpio);
		listenOnTrigger(gpio);

		while (true) {
			Thread.sleep(500);
		}
	}

	private static void listenOnTrigger(GpioController gpio) {
		GpioPinDigitalInput trigger = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00,
				PinPullResistance.PULL_DOWN);

		trigger.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				if (event.getState() == PinState.HIGH) {
					fireTrigger();
				}
			}

		});
	}

	private static void enablePin3(GpioController gpio) {
		gpio.setState(true, gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03));
	}

	private static void fireTrigger() {
		try {
			fireTriggerUnsafe();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void fireTriggerUnsafe() throws Exception {
		System.out.println("GOOOOAL!!!!");
		List<String> playlistFiles = getPlaylistFiles();
		String catCommand = "MP4Box -cat " + StringUtils.join(playlistFiles, " -cat ") + " "
				+ LAST_SCENE_FILENAME;
		run(catCommand).waitFor();
		run("omxplayer " + LAST_SCENE_FILENAME).waitFor();
		run("rm -f " + LAST_SCENE_FILENAME).waitFor();
	}

	private static Process run(String command) throws IOException {
		System.out.println("Running: " + command);
		return new ProcessBuilder(command.split(" ")).inheritIO().directory(WORKING_DIRECTORY).start();
	}

	private static List<String> getPlaylistFiles() throws IOException, FileNotFoundException {
		List<String> playlistFiles = new ArrayList<>();
		List<String> playlistLines = IOUtils.readLines(new FileInputStream(PLAYLIST_FILE));
		for (String playlistLine : playlistLines) {
			if (!playlistLine.startsWith("#")) {
				playlistFiles.add(playlistLine);
			}
		}
		return playlistFiles;
	}
}
