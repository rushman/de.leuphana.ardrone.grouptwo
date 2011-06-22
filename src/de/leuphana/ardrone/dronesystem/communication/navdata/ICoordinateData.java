package de.leuphana.ardrone.dronesystem.communication.navdata;

public interface ICoordinateData {

	/**
	 * theta forward-backward in milli degrees
	 */
	public float getPitch();
	/**
	 * altitude in centimeters (millimeters seems to be correct, but doc describe this as cm)
	 */
	public int getAltitude();
	// VORSICHT Richtung beachten
	/**
	 * estimated linear velocity in x direction (forward/backward)
	 */
	public float getVx();
	/**
	 * estimated linear velocity in y direction (up/Down)
	 */
	public float getVy();
	/**
	 * estimated linear velocity in z direction (tilt-Left/Right)
	 */
	public float getVz();
	/**
	 * psi; //tilt Left Right in milli degrees
	 */
	public float getYaw();
	/**
	 * phi //(roll) in milli degrees
	 */
	public float getRotate();
}
