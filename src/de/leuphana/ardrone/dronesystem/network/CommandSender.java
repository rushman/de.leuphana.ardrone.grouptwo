package de.leuphana.ardrone.dronesystem.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import de.leuphana.ardrone.dronesystem.domain.util.Config;

/**
 * Klasse zum Aufbauen einer UDP-Verbindung mit einem Rechner/Drone im Netzwerk
 * Zum verbinden mit der Drone eine neue Instanz dieser Klasse erzeugen Zum
 * Senden von Paketen an die Drone sendCommand nutzen
 * 
 * @author ICH
 * 
 */
public enum CommandSender {
	INSTANCE;
	private InetAddress inetAddress;
	private DatagramSocket commandSocket;

	// private SenderThread sender;

	private CommandSender() {
		// initialisiert eine Verbindung

	}

	public void open() {
		try {
			// kapselt die IP des anzusprechenden Netzteilnehmers
			inetAddress = InetAddress.getByName(Config.getIp());
			commandSocket = new DatagramSocket();
			commandSocket.setSoTimeout(3000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean isOpen() {
		return commandSocket.isConnected();
	}

	public InetAddress getInetAddress() {
		return inetAddress;
	}

	/**
	 * sendet den übergebenen String an die Drohne und fügt einen CarriageReturn
	 * linefeed hinzu
	 * 
	 * @param command
	 * @return
	 */
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
		if (inetAddress == null || commandSocket == null)
			open();
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
}
