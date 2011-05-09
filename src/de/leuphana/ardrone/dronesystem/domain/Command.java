package de.leuphana.ardrone.dronesystem.domain;

import java.text.MessageFormat;

import de.leuphana.ardrone.dronesystem.domain.util.Counter;

public class Command {
	private String commandMessage;
	public CmdValue value;
	public String info = "";

	public Command(CmdValue command) {
		this.commandMessage = command.asCommand();
		this.value = command;
	}

	public String getMessageWithCounter() {
		return String.format(commandMessage, Counter.get());
	}

	public Command(CmdValue command, String message) {
		this(command);
	}

	// @SuppressWarnings("")
	public String replace(Object... placeholder) {
		String[] strings = new String[placeholder.length];
		for (int i = 0; i < placeholder.length; i++) {
			strings[i] = placeholder[i].toString();
		}
		// System.out.println("anzahl placeholder " + placeholder.length);
		commandMessage = MessageFormat.format(commandMessage, strings);
		return commandMessage;
	}

	@Override
	public String toString() {
		return "Command [commandMessage=" + commandMessage + ", value="
				+ value.name() + "]";
	}

	public static Command newListCommand(String commandMessage) {
		Command command = new Command(CmdValue.LIST);
		command.commandMessage = commandMessage;
		return command;
	}

}
