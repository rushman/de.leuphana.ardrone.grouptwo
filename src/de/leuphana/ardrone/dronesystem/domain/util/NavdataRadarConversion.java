package de.leuphana.ardrone.dronesystem.domain.util;

import java.util.TimerTask;

import de.leuphana.ardrone.dronesystem.communication.navdata.ICoordinateData;

/**
 * Diese Klasse bereitet die Navdata Werte für die GUI auf.
 * 
 * @author cm und ls
 * 
 */

public class NavdataRadarConversion extends TimerTask {

	private float xRadarDistance;
	private float yRadarDistance;
	private float radarFacing;
	private long delay = 2000;
	private long period = 500;
	private long conversionFactor = period;

	public float getxRadarDistance() {
		return xRadarDistance;
	}

	public float getyRadarDistance() {
		return yRadarDistance;
	}

	public float getRadarFacing() {
		return radarFacing;
	}

	TimerTask timer = new NavdataRadarConversion();

	/**
	 * Aufruf des TimerTasks NavdataRadarConversion
	 * 
	 * @param timer
	 *            NavdataRadarConversion Objekt
	 * @param delay
	 *            Startverzögerung in ms
	 * @param period
	 *            Aufrufintervall in ms
	 * @throws IllegalStateException
	 */
	public void scheduleAtFixedRate(TimerTask timer, long delay, long period)
			throws IllegalStateException {

	}

	@Override
	public void run() {
		// TODO Datenabfrage, klappt noch nicht weil Interface noch nirgends
		// ausprogrammiert
		// xRadarDistance = ICoordinateData.getVx() * conversionFactor;
		// yRadarDistance = ICoordinateData.getVy() * conversionFactor;
		// radarFacing = Math.round(ICoordinateData.getRotate());

	}

}
