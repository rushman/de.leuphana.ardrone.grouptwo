package de.leuphana.ardrone.dronesystem.old.listener;

import java.util.EventListener;

public interface MessageListener extends EventListener{
	
	public void commandSend(String commandName, String message, boolean success);

}
