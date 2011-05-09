package de.leuphana.ardrone.dronesystem.network;

import java.net.DatagramSocket;
import java.net.SocketException;

class VideoReceiver {
	private DatagramSocket videoStreamSocket;

	public VideoReceiver() {
		try {
			videoStreamSocket = new DatagramSocket(5555);
			videoStreamSocket.setSoTimeout(3000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		// TODO not implemented yet
	}
}