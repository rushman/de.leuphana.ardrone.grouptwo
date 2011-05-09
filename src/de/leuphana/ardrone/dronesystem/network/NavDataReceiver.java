package de.leuphana.ardrone.dronesystem.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class NavDataReceiver {
	private DatagramSocket navDataSocket;
	private InetAddress address;

	public NavDataReceiver(InetAddress inetAddress) {
		try {
			this.address = inetAddress;
			navDataSocket = new DatagramSocket(5554);
			navDataSocket.setSoTimeout(3000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] receive()
	{
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		try {
			navDataSocket.receive(packet);
			return packet.getData();
		} catch (IOException e) {
			e.printStackTrace();
			return new byte[]{0};
		}
	}
}