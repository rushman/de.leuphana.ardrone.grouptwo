package de.leuphana.quadt.drone.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.leuphana.quadt.drone.CommandCounter;
import de.leuphana.quadt.drone.Util;

/**
 * Klasse zum Aufbauen einer UDP-Verbindung mit einem Rechner/Drone im Netzwerk
 * Zum verbinden mit der Drone eine neue Instanz dieser Klasse erzeugen Zum
 * Senden von Paketen an die Drone sendCommand nutzen
 * 
 * @author ICH
 * 
 */
public class DroneConnect {
	private InetAddress inetAddress;
	private DatagramSocket commandSocket;
//	private SenderThread sender;

	public DroneConnect(String ip) {
		// initialisiert eine Verbindung
		String intern_Ip;
		if (ip == null || ip.isEmpty())
			intern_Ip = "192.168.1.1";
		else
			intern_Ip = ip;
		try {
			// kapselt die IP des anzusprechenden Netzteilnehmers
			inetAddress = InetAddress.getByName(intern_Ip);
			// socket = new DatagramSocket(5556,inet_addr);
			commandSocket = new DatagramSocket();
			commandSocket.setSoTimeout(3000);
//			sender = new SenderThread();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public InetAddress getInetAddress()
	{
		return inetAddress;
	}

//	boolean isRunning = false;

//	public void start() {
////		if (!isRunning) {
////			sender.start();
////			isRunning = true;
////		}
//	}

//	public void setInitialized(boolean initialized) {
//		sender.setInitialized(initialized);
//	}
//
//	public void setFlying(boolean flying) {
//		sender.setFlying(flying);
//	}

	public void initNavDataReceiver() {

	}

	/**
	 * sendet den übergebenen String an die Drohne und fügt einen CarriageReturn
	 * linefeed hinzu
	 * 
	 * @param command
	 * @return
	 */
//	 public boolean sendCommand(String command) {
//	 return send(command + "\r");
//	 }
	public boolean sendCommand(String command) {
		return send(command + "\r");
	}


	/**
	 * verpackt einen String in ein Datenpaket und sendet dieses an die bei der
	 * Initialisierung angegebene IP-Adresse auf Port 5556.
	 * 
	 * @param command
	 * @return
	 */
	private synchronized boolean send(String command) {
		byte[] bytes = command.getBytes();
		DatagramPacket packet = new DatagramPacket(bytes, bytes.length,
				inetAddress, 5556);
		try {
			commandSocket.send(packet);
			System.out.println("NET: " + command);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void close() {
		commandSocket.close();
	}

	class VideoReceiver {
		private DatagramSocket videoStreamSocket;

		public VideoReceiver() {
			try {
				videoStreamSocket = new DatagramSocket(5555);
				videoStreamSocket.setSoTimeout(3000);
			} catch (SocketException e) {
				e.printStackTrace();
			}
			// TODO Auto-generated constructor stub
		}
	}
}
