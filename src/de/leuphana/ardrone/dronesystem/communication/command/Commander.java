package de.leuphana.ardrone.dronesystem.communication.command;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import de.leuphana.ardrone.dronesystem.domain.Command;
import de.leuphana.ardrone.dronesystem.network.CommandSender;

public class Commander {
	// while(true) 4 , e
	private static ScheduledExecutorService executorTassadar;
	private static Command commandInternal;
	private static ScheduledFuture<?> future;
	// 4 + e
	static Runnable probe = new Runnable() {

		@Override
		public void run() {
			CommandSender.INSTANCE.sendCommand(commandInternal
					.getMessageWithCounter());
		}
	};

	public static void start() {
		if (executorTassadar != null) {
			executorTassadar.shutdownNow();
		}
		startInternal();
	}

	public static void restart() {
		stop();
		startInternal();
	}

	public static void stop() {
		if (future != null) {
			boolean canceled = future.cancel(false);
			if (!canceled) {
				executorTassadar.shutdownNow();
			}
		}
	}

	public static void kill() {
		if (executorTassadar != null) {
			executorTassadar.shutdownNow();
			if (!executorTassadar.isShutdown())
				throw new RuntimeException(
						"Unable to terminate tassadar, please contact your overmind");
		}
	}

	public synchronized static void setCommand(Command command) {
		commandInternal = command;
	}

	private static void startInternal() {
		executorTassadar = Executors.newScheduledThreadPool(1);
		future = executorTassadar.scheduleAtFixedRate(probe, 0, 300,
				TimeUnit.MILLISECONDS);
	}

}
