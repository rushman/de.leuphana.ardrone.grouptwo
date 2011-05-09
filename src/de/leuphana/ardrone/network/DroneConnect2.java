package de.leuphana.ardrone.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * Klasse zum Aufbauen einer UDP-Verbindung mit einem Rechner/Drone im Netzwerk
 * Zum verbinden mit der Drone eine neue Instanz dieser Klasse erzeugen Zum
 * Senden von Paketen an die Drone sendCommand nutzen
 * 
 * @author ICH
 * 
 */
public class DroneConnect2 {
//	private InetAddress inet_addr;
//	private DatagramSocket commandSocket;
////	private SenderThread sender;
//
//	public DroneConnect2(String ip) {
//		// initialisiert eine Verbindung
//		String intern_Ip;
//		if (ip == null || ip.isEmpty())
//			intern_Ip = "192.168.1.1";
//		else
//			intern_Ip = ip;
//		try {
//			// kapselt die IP des anzusprechenden Netzteilnehmers
//			inet_addr = InetAddress.getByName(intern_Ip);
//			// socket = new DatagramSocket(5556,inet_addr);
//			commandSocket = new DatagramSocket();
//			commandSocket.setSoTimeout(3000);
////			sender = new SenderThread();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SocketException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	boolean isRunning = false;
//
//	public void start() {
////		if (!isRunning) {
////			sender.start();
////			isRunning = true;
////		}
//	}
//
//	public void addInitCommand(String command) {
////		sender.addInitCommand(command);
//		sendCommand(command);
//	}
//
////	public void setInitialized(boolean initialized) {
////		sender.setInitialized(initialized);
////	}
////
////	public void setFlying(boolean flying) {
////		sender.setFlying(flying);
////	}
//
//	public void initNavDataReceiver() {
//
//	}
//
//	/**
//	 * sendet den übergebenen String an die Drohne und fügt einen CarriageReturn
//	 * linefeed hinzu
//	 * 
//	 * @param command
//	 * @return
//	 */
////	 public boolean sendCommand(String command) {
////	 return send(command + "\r");
////	 }
//	public boolean sendCommand(String command) {
//		return sender.setCommand(command);
//	}
//
////	class SenderThread extends Thread {
////		private String currentCommand = null;
////		private boolean flying = false;
////		private boolean singleCommand = false;
////		private boolean initialized = false;
////		private boolean initializingInit = false;
////		private List<String> initCommands;
////
////		public void addInitCommand(String command) {
////			initCommands.add(command);
////		}
////
////		public SenderThread() {
////			initCommands = new ArrayList<String>();
////		}
////
////		public boolean setCommand(String command) {
////			currentCommand = command;
////			return true;
////		}
////
////		public void setInitialized(boolean initialized) {
////			this.initialized = initialized;
////		}
////
////		public void setInitializingInit(boolean initialing) {
////			this.initializingInit = initialing;
////		}
////
////		public void setFlying(boolean flying) {
////			this.flying = flying;
////		}
////
//////		@Override
//////		public void run() {
//////			// TODO Auto-generated method stub
//////			while (true) {
//////				if (initializingInit) {
//////					Util.sleepMillis(20);
//////					continue;
//////				}
//////				if (!initialized) {
//////					if (initCommands.isEmpty()) {
//////						initialized = true;
//////						continue;
//////					} else {
//////						System.out.println("SENDER: " + initCommands.get(0));
//////						sendCommand(initCommands.remove(0));
//////						Util.sleepMillis(20);
//////						continue;
//////					}
//////				}
////////				if (initialized && flying && currentCommand == null) {
////////					sendCommand("AT*PCMD=" + (CommandCounter.getCounter())
////////							+ ",1,0,0,0,0" + "\r" + "AT*REF="
////////							+ (CommandCounter.getCounter()) + ",290718208");
////////					continue;
////////				}
//////				if (currentCommand != null) {
//////					sendCommand(currentCommand);
//////					System.out.println("SENDER: " + currentCommand);
//////					if (singleCommand)
//////						currentCommand = null;
//////					Util.sleepMillis(20);
//////					continue;
//////				}
//////			}
//////			// super.run();
//////		}
//
//		/**
//		 * sendet den übergebenen String an die Drohne und fügt einen
//		 * CarriageReturn linefeed hinzu
//		 * 
//		 * @param command
//		 * @return
//		 */
//		public synchronized boolean sendCommand(String command) {
//			return send(command + "\r");
//		}
//
//		/**
//		 * verpackt einen String in ein Datenpaket und sendet dieses an die bei
//		 * der Initialisierung angegebene IP-Adresse auf Port 5556.
//		 * 
//		 * @param command
//		 * @return
//		 */
//		private synchronized boolean send(String command) {
//			byte[] bytes = command.getBytes();
//			DatagramPacket packet = new DatagramPacket(bytes, bytes.length,
//					inet_addr, 5556);
//			try {
//				commandSocket.send(packet);
//				return true;
//			} catch (IOException e) {
//				e.printStackTrace();
//				return false;
//			}
//		}
//
//	}
//
//	/**
//	 * verpackt einen String in ein Datenpaket und sendet dieses an die bei der
//	 * Initialisierung angegebene IP-Adresse auf Port 5556.
//	 * 
//	 * @param command
//	 * @return
//	 */
//	private synchronized boolean send(String command) {
//		byte[] bytes = command.getBytes();
//		DatagramPacket packet = new DatagramPacket(bytes, bytes.length,
//				inet_addr, 5556);
//		try {
//			commandSocket.send(packet);
//			return true;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	public void close() {
//		commandSocket.close();
//	}
//
//	class DataReceiver {
//		private DatagramSocket navDataSocket;
//
//		public DataReceiver() {
//			try {
//				navDataSocket = new DatagramSocket(5554);
//				navDataSocket.setSoTimeout(3000);
//			} catch (SocketException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	class VideoReceiver {
//		private DatagramSocket videoStreamSocket;
//
//		public VideoReceiver() {
//			try {
//				videoStreamSocket = new DatagramSocket(5555);
//				videoStreamSocket.setSoTimeout(3000);
//			} catch (SocketException e) {
//				e.printStackTrace();
//			}
//			// TODO Auto-generated constructor stub
//		}
//	}
//
//	public void setInitializingInit(boolean b) {
//		sender.setInitializingInit(b);
//		if (!b)
//			setInitialized(false);
//
//	}
}
