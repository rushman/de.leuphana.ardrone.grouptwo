package de.leuphana.ardrone.dronesystem.domain.util;

import java.util.TimerTask;


public class NavdataRadarConversion extends TimerTask{
	
	TimerTask timer = new NavdataRadarConversion();
	
	public void scheduleAtFixedRate(TimerTask timer, long delay, long period){
	delay = 2000;
	period = 500;
	}
		
	
	

	@Override
	public void run() {
		// TODO Navdata abfragen
	//x = ICoordinateData.getVx();
		// TODO umrechnen
		// TODO ausgeben
		
	}
	


}



