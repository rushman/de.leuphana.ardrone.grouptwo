package de.leuphana.ardrone.dronesystem.domain.util;


public class Config {

	private static String ip = "192.168.1.1";

	public static String getIp() {
		String intern_Ip;
		if (ip == null || ip.isEmpty())
			intern_Ip = "192.168.1.1";
		else
			intern_Ip = ip;
		return intern_Ip;
	}

}
