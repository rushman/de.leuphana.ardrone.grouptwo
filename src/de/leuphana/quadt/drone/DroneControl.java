package de.leuphana.quadt.drone;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.atomic.AtomicBoolean;

import de.leuphana.quadt.drone.domain.Direction;
import de.leuphana.quadt.drone.listener.MessageListener;
import de.leuphana.quadt.drone.network.DroneConnect;

public class DroneControl {
	private DroneCommandSender commands;
	private Float tilt = 0f, vertical = 0f, horizontal = 0f, rotation = 0f;
	private int baseSpeed = 100;
	private int horzSpeed =  3;
	private int verticalSpeed =  1;
	private int tiltSpeed =  3;
	private int rotationSpeed =  1;
	private AtomicBoolean started = new AtomicBoolean(false);
	PropertyChangeSupport pcs =  new PropertyChangeSupport( this );
	
	public DroneControl(DroneConnect connection) {
		commands = new DroneCommandSender(connection);
	}
	
	public DroneCommandSender getDroneCommands()
	{
		return commands;
	}
	

	private float speedToFloat(int speed)
	{
		return ((baseSpeed*speed)/10.0f)/100.0f;
	}
	
	public int getBaseSpeed() {
		return baseSpeed;
	}

	public void setBaseSpeed(int baseSpeed) {
		int old = this.baseSpeed;
		this.baseSpeed=ensureIntInRange(baseSpeed);
		firePropertyChange("baseSpeed", old, baseSpeed);
	}

	private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		pcs.firePropertyChange(propertyName, oldValue, newValue);
		System.out.println(propertyName + " " + newValue);
	}
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{	
		pcs.addPropertyChangeListener(listener);
	}
	public void addPropertyChangeListener(String propertyName,PropertyChangeListener listener)
	{	
		pcs.addPropertyChangeListener(propertyName, listener);
	}
	
	public int getHorzSpeed() {
		return horzSpeed;
	}

	public void setHorzSpeed(int horzSpeed) {
		int old = this.horzSpeed;
		this.horzSpeed=ensureIntInRange(horzSpeed);
		firePropertyChange("horzSpeed", old, horzSpeed);
	}

	public int getVerticalSpeed() {
		return verticalSpeed;
	}

	public void setVerticalSpeed(int verticalSpeed) {
		int old = this.verticalSpeed;
		this.verticalSpeed=ensureIntInRange(verticalSpeed);
		firePropertyChange("verticalSpeed", old, verticalSpeed);
	}

	public int getTiltSpeed() {
		return tiltSpeed;
	}

	public void setTiltSpeed(int tiltSpeed) {
		int old = this.tiltSpeed;
		this.tiltSpeed=ensureIntInRange(tiltSpeed);
		firePropertyChange("tiltSpeed", old, tiltSpeed);
	}

	public int getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(int rotationSpeed) {
		int old = this.rotationSpeed;
		this.rotationSpeed=ensureIntInRange(rotationSpeed);
		firePropertyChange("rotationSpeed", old, rotationSpeed);
	}
	
	private int ensureIntInRange(int integer)
	{
		return ensureIntInRange(integer,0, 100);
	}
	private int ensureIntInRange(int integer, int lower, int upper)
	{
		int zahl;
		if(integer>upper)
			zahl=100;
		else if(lower<0)
			zahl=0;
		else
			zahl = integer;
		return zahl;
	}
	
	
	/**
	 * 
	 * @param tilt negative moves left, positive moves right
	 * @param vertical positive higher, negative lower
	 * @param horizontal negative forward, positive backward
	 * @param rotation negative turns left, positive turns to right
	 * @return
	 */
	protected void move(Float tilt, Float vertical, Float horizontal,
			Float rotation) {
		if(!started.get())
			return;
		setValue(tilt, Direction.TILT);
		setValue(vertical, Direction.VERTICAL);
		setValue(horizontal, Direction.HORIZONTAL);
		setValue(rotation, Direction.ROTATION);
		commands.move(this.tilt.floatValue(), this.horizontal.floatValue(), this.vertical.floatValue(), this.rotation.floatValue());
		// control.move(tilt, horizontal, vertical, rotation);
	}

	private void setValue(Float f, Direction d) {
		if (f == null)
			return;
		switch (d) {
		case TILT:
			tilt = f;
			break;
		case VERTICAL:
			vertical = f;
			break;
		case HORIZONTAL:
			horizontal = f;
			break;
		case ROTATION:
			rotation = f;
			break;
		default:
			land();
		}
	}

	public void reset() {
		commands.reset();
	}

	public boolean start() {
		if (!started.get()) {
			started.set(true);
			return commands.start();
		}
		return true;
	}

	public boolean land() {
		started.set(false);
		return commands.land();
	}

	public void hover() {
		commands.hover();
	}
	
	public void tiltLeft(boolean pressed) {
		Float f = -speedToFloat(tiltSpeed);
		if (!pressed) {
			f = 0f;
		}
		move(f, null, null, null);
		
	}
	
	public void tiltRight(boolean pressed) {
		Float f = speedToFloat(tiltSpeed);
		if (!pressed) {
			f = 0f;
		}
		move(f, null, null, null);
		
	}

	public void up(boolean pressed) {
		Float f = speedToFloat(verticalSpeed);
		if (!pressed) {
			f = 0f;
		}
		move(null, f, null, null);
	}

	public void down(boolean pressed) {
		Float f = -speedToFloat( verticalSpeed);
		if (!pressed) {
			f = 0f;
		}
		move(null, f, null, null);
	}

	public void forward(boolean pressed) {
		Float f = -speedToFloat(horzSpeed);
		if (!pressed) {
			f = 0f;
		}
		move(null, null, f, null);
	}

	public void backward(boolean pressed) {
		Float f = speedToFloat(horzSpeed);
		if (!pressed) {
			f = 0f;
		}
		move(null, null, f, null);
	}

	public void rotLeft(boolean pressed) {
		Float f = -speedToFloat(rotationSpeed);
		if (!pressed) {
			f = 0f;
		}
		move(null, null, null, f);
	}

	public void rotRight(boolean pressed) {
		Float f = speedToFloat(rotationSpeed);
		if (!pressed) {
			f = 0f;
		}
		move(null, null, null, f);
	}

	public void addCommandsListener(MessageListener listener)
	{
		commands.addListener(listener);
	}
	public void removeCommandsListener(MessageListener listener)
	{
		commands.removeListener(listener);
	}
	
	
	
	
}
