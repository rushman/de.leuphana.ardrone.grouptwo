package de.leuphana.ardrone.dronesystem.old;

import java.awt.KeyEventDispatcher;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JFrame;

import de.leuphana.ardrone.dronesystem.old.listener.MessageListener;
import de.leuphana.ardrone.dronesystem.old.network.DroneConnect;

public class Controller implements KeyEventDispatcher, MessageListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7467184653772941336L;
	private DroneControl droneControl;

	private DroneConnect droneConnect;
	
	private char lastChar;
	private JFrame frame;
	
	
	public Controller(JFrame frame) {
		// frame.addKeyListener(this);
		this.frame = frame;
		droneConnect = new DroneConnect("");
		droneControl = new DroneControl(droneConnect);
		droneControl.addCommandsListener(this);
	}
	public DroneControl getDroneControl() {
		return droneControl;
	}
	
	public void addDroneControlListener(MessageListener listener)
	{
		droneControl.addCommandsListener(listener);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
//			System.out.println("ED:key pressed: " + e.getKeyChar());
			keyPressed(e);
		} else if (e.getID() == KeyEvent.KEY_RELEASED) {
//			System.out.println("ED:key released: " + e.getKeyChar());
			keyReleased(e);
		} else if (e.getID() == KeyEvent.KEY_TYPED) {
			// System.out.println("ED:key typed: " + e.getKeyChar());
		}
		return false;
	}

	private void keyReleased(KeyEvent e) {
		char currentChar = e.getKeyChar();
		charToCommand(currentChar, false);

	}

	private void keyPressed(KeyEvent e) {
		char currentChar = e.getKeyChar();
		charToCommand(currentChar, true);
	}

	private void charToCommand(char current, boolean pressed) {
		char c = Character.toLowerCase(current);
		switch (c) {
		case 'w':
			forward(pressed);
			break;
		case 'n':
			rotLeft(pressed);
			break;
		case 's':
			backward(pressed);
			break;
		case 'm':
			rotRight(pressed);
			break;
		case 'u':
			up(pressed);
			break;
		case 'i':
			down(pressed);
			break;
		case 'a':
			tiltLeft(pressed);
			break;
		case 'd':
			tiltRight(pressed);
			break;
		case 'h':
			hover(pressed);
			break;
		case KeyEvent.VK_SPACE:
		case 'l':
			if (pressed)
				land();
			break;
		case KeyEvent.VK_ENTER:
		case 'k':
			if (pressed)
				start();
			break;
		default:
			break;
		}
	}

	private void hover(boolean pressed) {
		droneControl.hover();
		
	}

	public void start() {
		droneControl.start();
	}

	public void land() {
		droneControl.land();
	}
	public void tiltLeft(boolean pressed) {
		droneControl.tiltLeft(pressed);
	}
	
	
	
	public void tiltRight(boolean pressed) {
		droneControl.tiltRight(pressed);
		
	}

	public void up(boolean pressed) {
		droneControl.up(pressed);
	}

	public void down(boolean pressed) {
		droneControl.down(pressed);
	}

	public void forward(boolean pressed) {
		droneControl.forward(pressed);
	}

	public void backward(boolean pressed) {
		droneControl.backward(pressed);
	}

	public void rotLeft(boolean pressed) {
		droneControl.rotLeft(pressed);
	}

	public void rotRight(boolean pressed) {
		droneControl.rotRight(pressed);
	}

	@Override
	public void commandSend(String commandName, String message, boolean success) {
//		System.out.println(commandName + " : " + message);
	}

	public void reset() {
		droneControl.reset();
		
	}

	public void setGameMode(boolean selected) {
		droneControl.getDroneCommands().setGameMode(selected);
		
	}

	public void setOutdoor(boolean selected) {
		droneControl.getDroneCommands().setOutdoor(selected);
		
	}

	public void setIndoorShell(boolean selected) {
		droneControl.getDroneCommands().setShell(selected);
	}

}
