package de.leuphana.ardrone.dronesystem.domain.util;

public class Util {
	
	public static void sleepSeconds(int seconds)
	{
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void sleepMillis(long millis)
	{
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public static void sleepMillis(int millis)
//	{
//		try {
//			Thread.sleep(millis);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
