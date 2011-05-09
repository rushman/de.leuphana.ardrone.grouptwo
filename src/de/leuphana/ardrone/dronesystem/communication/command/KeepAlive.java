package de.leuphana.ardrone.dronesystem.communication.command;

import static de.leuphana.ardrone.dronesystem.domain.CmdValue.COMMAND_RESETWATCHDOG;

import java.util.Timer;
import java.util.TimerTask;

import de.leuphana.ardrone.dronesystem.network.CommandSender;

public class KeepAlive {
	private static Timer t;

	private KeepAlive() {
	}

	public static void start() {
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				CommandSender.INSTANCE.sendCommand(COMMAND_RESETWATCHDOG
						.asCommand());
			}
		}, 0 /* initial delay */, 30 /* period */);
	}

	public static void stop() {
		t.cancel();
	}
}
