package de.leuphana.ardrone.dronesystem.old.domain;

import java.text.MessageFormat;


public class Command {
	public String commandMessage;
	public Commands value;
	public String info = "";
	public Command(Commands command) {
		this.commandMessage = command.getCommandString();
		this.value = command;
	}
	
//	public Command(Commands command, long counter)
//	{
//		this(command);
//		commandMessage = String.format(command.getCommandString(), counter);
//	}
	public Command(Commands command, String message)
	{
		this(command);
//		replace("a", "b");
//		commandMessage;
	}
	
	
	public String replace(Object... placeholder)
	{
		String[] strings = new String[placeholder.length];
		for (int i = 0; i < placeholder.length; i++) {
			strings[i] = placeholder[i].toString();
		}
//		System.out.println("anzahl placeholder " + placeholder.length);
		commandMessage = MessageFormat.format(commandMessage, strings);
		return commandMessage;
	}

	@Override
	public String toString() {
		return "Command [commandMessage=" + commandMessage + ", value=" + value.name()
				+ "]";
	}

	public static Command newListCommand(String commandMessage)
	{
		Command command = new Command(Commands.LIST);
		command.commandMessage = commandMessage;
		return command;
	}
	
	
}
