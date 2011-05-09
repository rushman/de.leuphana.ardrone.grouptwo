package de.leuphana.ardrone;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;


public class CommandDispatcher extends Thread {
	
	private static ConcurrentHashMap<String,CommandDispatcher> dispatchers = new ConcurrentHashMap<String, CommandDispatcher>();
	
	AtomicBoolean executeCommand = new AtomicBoolean(true);
	Controller controller;
	Method method;
	
	public void setExecuting(boolean b)
	{
		executeCommand.set(b);
	}
	
	public static boolean stopDispatcher(String methodName)
	{
		if(methodName == null)
			return false;
		CommandDispatcher commandDispatcher = dispatchers.get(methodName);
		if(commandDispatcher != null )
		{
//			commandDispatcher.setExecuting(false);
			commandDispatcher.end();
		}
//		commandDispatcher = new CommandDispatcher(controller, methodName);
//		return commandDispatcher;
		return true;
	}
	
	public static CommandDispatcher getNewDispatcher(Controller controller,String methodName)
	{
		if(methodName == null)
			return null;
		CommandDispatcher commandDispatcher = dispatchers.get(methodName);
		if(commandDispatcher != null )
		{
			commandDispatcher.setExecuting(false);
			commandDispatcher.end();
		}
		commandDispatcher = new CommandDispatcher(controller, methodName);
		dispatchers.put(methodName, commandDispatcher);
		return commandDispatcher;
	}
	
	private CommandDispatcher(Controller controller,String methodName) {
		this.controller = controller;
		try {
			method = controller.getClass().getMethod(methodName, boolean.class);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		while(executeCommand.get())
		{
			if(method !=null)
			{
				try {
					method.invoke(controller, true);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			controller.tiltLeft(true);
			}
			Util.sleepMillis(20);
		}
	}
	
	public void end()
	{
		Method method = this.method;
		setExecuting(false);
		Util.sleepMillis(100);
		try {
			method.invoke(controller, false);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
