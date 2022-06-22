package de.corruptedbytes.webserver.packets;

import org.java_websocket.WebSocket;

public abstract class Packet {
	
	public abstract String getPacket();

	public abstract void onPacketReceive(WebSocket webSocket, String message)
			throws Exception;

}
