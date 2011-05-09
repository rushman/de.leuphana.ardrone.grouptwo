package de.leuphana.quadt.drone;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.leuphana.quadt.drone.domain.Command;
import de.leuphana.quadt.drone.domain.Commands;
import de.leuphana.quadt.drone.domain.DroneState;
import de.leuphana.quadt.drone.listener.MessageListener;
import de.leuphana.quadt.drone.network.DroneConnect;

public class DroneCommandSender {

	private static final String CR = "\r";

	Set<MessageListener> listeners;
	private boolean gameMode = false;
	private boolean outdoor = false;
	private boolean shell = true;
	private float maxEulerAngle = 0.25f;
	private float maxVz = 2000.0f;
	private float maxYaw = 2.0f;
	private int maxHight = 1300;
	private boolean navdata = false;
	private DroneState droneState = DroneState.CONFIG;
	private SenderThread sender;

	public boolean isShell() {
		return shell;
	}

	public void setShell(boolean shell) {
		this.shell = shell;
		notifyMessageObservers("Shell Mode changed to",
				Boolean.toString(this.shell), true);
	}

	public boolean isOutdoor() {
		return outdoor;
	}

	public void setOutdoor(boolean outdoor) {
		this.outdoor = outdoor;
		notifyMessageObservers("Outdoor Mode changed to", outdoor ? "OUTDOOR"
				: "INDOOR", true);
	}

	public boolean isGameMode() {
		return gameMode;
	}

	public void setGameMode(boolean gameMode) {
		this.gameMode = gameMode;
		notifyMessageObservers("Game Mode changed to", gameMode ? "GAMEMODE"
				: "DEFAULT", true);
	}

	public DroneCommandSender(DroneConnect connection) {
		listeners = new HashSet<MessageListener>();
		DroneConnect conn;
		if (connection == null)
			conn = new DroneConnect("");
		else
			conn = connection;
		sender = new SenderThread(conn);
		CommandCounter.reset();
		sender.start();
		init();
	}

	public void addMaxHightCommand(int hight) {
		Command command = new Command(Commands.COMMAND_CONF_MAXHIGHT);
		command.replace(Float.toString(hight));
		// command.commandMessage = String.format(command.commandMessage,
		// getCounter());
		addInitCommand(command);
	}

	public void addMaxYawCommand(float yaw) {
		Command command = new Command(Commands.CONF_YAW);
		command.replace(Float.toString(yaw));
		addInitCommand(command);
	}

	public void addMaxEulerAngleCommand(float eulerAngle) {
		Command command = new Command(Commands.CONF_EULER_ANGLE);
		command.replace(Float.toString(eulerAngle));
		addInitCommand(command);
	}

	public void addMaxVzCommand(float maxvz) {
		Command command = new Command(Commands.CONF_VZ_MAX);
		command.replace(Float.toString(maxvz));
		addInitCommand(command);
	}

	public void init() {
		this.setDroneState(DroneState.CONFIG);

		addInitCommand(Commands.COMMAND_RESETWATCHDOG);
		addMaxHightCommand(maxHight);
		addMaxEulerAngleCommand(maxEulerAngle);
		addMaxVzCommand(maxVz);
		addMaxYawCommand(maxYaw);
		if (navdata) {
			addInitCommand(Commands.CONF_NAVDATA_TRUE);
		}
		// if (outdoor)
		// addInitCommandWithCounter(Commands.CONF_OUTDOOR);
		// else
		// addInitCommandWithCounter(Commands.CONF_INDOOR);
		// if (shell)
		// addInitCommandWithCounter(Commands.CONF_SHELL_TRUE);
		// else
		// addInitCommandWithCounter(Commands.CONF_SHELL_FALSE);
		// if (gameMode)
		// addInitCommandWithCounter(Commands.CONF_CONTROLLEVEL_RACE);
		// else
		// addInitCommandWithCounter(Commands.CONF_CONTROLLEVEL_DEFAULT);
		addInitCommand(Commands.COMMAND_CONF_TRIM);
		addInitCommand(Commands.COMMAND_CONF_TRIM);
		// addInitCommand(Commands.COMMAND_CONF_TRIM);

		// addInitCommand(Commands.COMMAND_CONF_TRIM);
		// addInitCommand(Commands.COMMAND_CONF_PMODE);
		// addInitCommand(Commands.COMMAND_CONF_MISC);
		// addInitCommand(Commands.COMMAND_LAND);
		// addInitCommand(Commands.COMMAND_HOVER);
		// addInitCommand(Commands.COMMAND_LAND);

		if (navdata) {
			// "AT*CONFIG=" +
			// NUM_STR(nbSequence)+",\"general:navdata_demo\",\"TRUE\"\r"
		}
		this.setDroneState(DroneState.INITIALIZING);
		// connection.setInitialized(true);

		// getCounter();
	}

	private void addInitCommand(Commands command) {
		addInitCommand(new Command(command));

	}

	private void addInitCommand(Command command) {
		sender.addInitCommand(command);
	}

	// private void sendAddCounter(String name, String command) {
	// send(name, addCounter(command));
	// }

	public void reset() {
		land();
		// Util.sleepSeconds(3);
		// CommandCounter.reset();
		// connection.setInitialized(false);
		Util.sleepSeconds(1);
		init();
	}

	public void hover() {
		// send("HOVER", addCounter(COMMAND_HOVER));
		sender.setCommand(new Command(Commands.COMMAND_HOVER));
	}

	public boolean start() {
		sender.setCommand(new Command(Commands.COMMAND_START));
		return true;
	}

	public boolean land() {
		sender.setCommand(new Command(Commands.COMMAND_LAND));
		return true;
	}

	/**
	 * 
	 * @param f
	 *            negative is left, positive turns to right
	 * @return
	 */
	public void turnHorz(float f) {
		move(0, 0, 0, f);

	}

	public void move(float seitw, float horz, float vertical, float rotate) {
		// String formattedMessage = String.format(COMMAND_NAV, getCounter(),
		// new Float(seitw), new Float(horz),
		// Float.floatToIntBits(vertical),
		// Float.floatToIntBits(rotate),gameMode?0:1);
		Command command = new Command(Commands.COMMAND_NAV);
		// command.commandMessage = MessageFormat.format(
		int mode = gameMode ? 0 : 1;
		command.replace(mode, toIntString(seitw), toIntString(horz),
				toIntString(vertical), toIntString(rotate));
		String name = MessageFormat
				.format("MOVE tilt: {0},horizontal: {1},vertical: {2},rotate: {3} {4}{5}",
						seitw, horz, vertical, rotate, "test", "test5");
		command.info = name;
		// name
		// =String.format("MOVE tilt: %1$d,horizontal: %2$d,vertical: {0},rotate: {0}",
		// seitw,horz,vertical,rotate);
		// formattedMessage = formattedMessage + "\r"
		// + String.format(COMMAND_START, getCounter());
		sender.setCommand(command);
	}

	public String toIntString(float f) {
		int intBits = Float.floatToIntBits(f);
		return Integer.toString(intBits);
	}

	public DroneState setDroneState(DroneState state) {
		this.droneState = state;
		return droneState;
	}

	public synchronized void sendCommand(Command command) {
		String message = "";
		message = addCounter(command.commandMessage);
		boolean succeeded = connection.sendCommand(message);
		if (command.value == Commands.COMMAND_RESETWATCHDOG)
			CommandCounter.reset();
		notifyMessageObservers(command.value.name() + " " + command.info,
				message, succeeded);
	}

	public synchronized void sendCommand(Commands command) {
		sendCommand(new Command(command));
	}

	DroneConnect connection;

	class SenderThread extends Thread {
		// private String currentCommand = null;
		private Command currentCommand = null;
		private boolean singleCommand = false;
		// private List<String> initCommands;
		ConcurrentLinkedQueue<Command> initCommandQueue;

		public void addInitCommand(Command command) {
			// initCommands.add(command);
			initCommandQueue.add(command);
		}

		public SenderThread(DroneConnect conn) {
			// initCommands = new ArrayList<String>();
			initCommandQueue = new ConcurrentLinkedQueue<Command>();
			// connection = new DroneConnect("");
			connection = conn;
			// connection.
		}

		public synchronized boolean setCommand(Command command) {
			currentCommand = command;
			return true;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				switch (droneState) {
				case CONFIG:
					// Util.sleepMillis(30);
					// continue;
					// sendCommand(Commands.COMMAND_LAND);
					break;
				case INITIALIZING:
					if (initCommandQueue.isEmpty()) {
						// sendCommand(Commands.COMMAND_HOVER);
						droneState = DroneState.READY;
						// FIXME possible bug!
						// CommandCounter.reset();
						break;
					} else {
						Command command = initCommandQueue.poll();
						// System.out.println("SENDER: " + command);
						sendCommand(command);
						Util.sleepMillis(20);
						sendCommand(Commands.COMMAND_ACK);
						break;
					}
				case READY:
					if (currentCommand == null) {
						sendCommand(Commands.COMMAND_LAND);
						break;
					}
					if (currentCommand.value == Commands.COMMAND_START) {
						droneState = DroneState.FLYING;
						System.err.println("FLYING");
					} else if (currentCommand.value == Commands.COMMAND_LAND) {
						System.err.println("landen obwohl gelandet");
						droneState = DroneState.READY;
					} else {
						sendCommand(Commands.COMMAND_LAND);
						//TODO
						break;
					}
					sendCommand(currentCommand);
					currentCommand = null;
					break;
				case FLYING:
					if (currentCommand == null) {
						sendCommand(Commands.COMMAND_START);
						break;
					}

					if (currentCommand.value == Commands.COMMAND_LAND)
						droneState = DroneState.READY;
					sendCommand(currentCommand);
					// System.out.println("SENDER: " + currentCommand);
					if (singleCommand)
						currentCommand = null;

					break;
				case ERROR:
					System.err.println("ERROR");
					sendCommand(Commands.COMMAND_LAND);
					break;
				case EMERGENCY:
					System.err.println("ERROR");
					sendCommand(Commands.COMMAND_LAND);
					break;
				default:
					break;
				}
				Util.sleepMillis(30);
			}
			// super.run();
		}
	}

	private void notifyMessageObservers(String commandName, String command,
			boolean succeeded) {
		for (MessageListener listener : listeners) {
			listener.commandSend(commandName, command, succeeded);
		}

	}

	/**
	 * fügt den aktuellen Wert des Kommandozählers in das übergebene Kommando
	 * ein. Dies funktioniert nur für Befehle, die nur den Kommandoformatstring
	 * enthalten, also Strings der Form:
	 * irgendwas=%1$d,irgendwasAnderesOhneFormatString" an die Stelle von %1$d
	 * wird der aktuelle Wert des Kommandozählers eingesetzt. Sollen mehrere
	 * Parameter ersetzt werden ist die getCounter() Methode in Kombination mit
	 * FormatStrings zu nutzen
	 * 
	 * @see de.leuphana.quadt.drone.DroneCommandSender#turnHorz(float f)
	 * @param command
	 * @return
	 */
	private String addCounter(String command) {

		return String.format(command, getCounter());
	}

	/**
	 * get the commandCounterValue. the value is increased by one with every
	 * call of this method
	 * 
	 * @return actual value of the command counter
	 */
	private long getCounter() {
		return CommandCounter.getCounter();
	}

	public void addListener(MessageListener listener) {
		listeners.add(listener);
	}

	public void removeListener(MessageListener listener) {
		listeners.remove(listener);
	}

}
