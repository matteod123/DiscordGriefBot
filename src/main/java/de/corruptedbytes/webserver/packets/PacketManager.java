package de.corruptedbytes.webserver.packets;

import java.util.concurrent.CopyOnWriteArrayList;

import org.java_websocket.WebSocket;

import de.corruptedbytes.webserver.packets.impl.*;

public class PacketManager {

	private CopyOnWriteArrayList<Packet> packets = new CopyOnWriteArrayList<>();

	public PacketManager() {
		getPackets().add(new PacketSetup());
		getPackets().add(new PacketGET());
		getPackets().add(new PacketNuke());
	}

	public CopyOnWriteArrayList<Packet> getPackets() {
		return packets;
	}

	public void reload() {
		packets.clear();
		new PacketManager();
	}

	public void callPacket(WebSocket webSocket, String message) {
		String[] array = message.split("\\|");
		String packet = array[0];
		String value = array[1];
		
		for (Packet c : getPackets()) {
			if (c.getPacket().startsWith(packet)) {
				try {
					c.onPacketReceive(webSocket, value);
				} catch (Exception ignored) { }
				return;
			}
		}
	}

}
