package de.leuphana.ardrone.dronesystem.domain.util;

import java.util.TimerTask;

import de.leuphana.ardrone.dronesystem.communication.navdata.ICoordinateData;

/**
 * Diese Klasse bereitet die Navdata Werte für die GUI auf.
 * 
 * @author cm und ls
 * 
 */

public class NavDataRadarConversion extends TimerTask implements NavDataRadarConversionController{

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

	TimerTask timer = new NavDataRadarConversion();

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
		NavDataRadarConversion navDataRadarConversion = new NavDataRadarConversion();
		// TODO Datenabfrage testen
		xRadarDistance = ((ICoordinateData) navDataRadarConversion).getVx() * conversionFactor;
		yRadarDistance = ((ICoordinateData) navDataRadarConversion).getVy() * conversionFactor;
		// TODO Rundungsfkt. mit negativwerten beachten
		radarFacing = Math.round(((ICoordinateData) navDataRadarConversion).getRotate());
		System.out.println("RadarData : xRadarDistance: " + xRadarDistance + " yRadarDistance : " + yRadarDistance + " radarFacing : " + radarFacing);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
