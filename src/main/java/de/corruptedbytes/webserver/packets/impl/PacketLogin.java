package de.corruptedbytes.webserver.packets.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.java_websocket.WebSocket;

import de.corruptedbytes.GriefBot;
import de.corruptedbytes.User;
import de.corruptedbytes.utils.Config;
import de.corruptedbytes.utils.MD5;
import de.corruptedbytes.webserver.packets.Packet;

public class PacketLogin extends Packet {

	@Override
	public String getPacket() {
		return "LOGIN";
	}

	@Override
	public void onPacketReceive(WebSocket webSocket, String message) throws Exception {
		User user = Config.getUser();
		String[] array = new String(Base64.getDecoder().decode(message), StandardCharsets.UTF_8).split("\\:");
		
		if (user != null) {
			String username = new String(Base64.getDecoder().decode(array[0]), StandardCharsets.UTF_8);
			String password = array[1];
			
			if (username.trim().equalsIgnoreCase(user.getUsername()) && password.trim().equals(user.getPassword())) {
				GriefBot.getInstance().setSeed(Base64.getEncoder().encodeToString((System.currentTimeMillis() + "").getBytes()));
				String userString = Base64.getEncoder().encodeToString((Base64.getEncoder().encodeToString(username.toLowerCase().getBytes()) + ":" + password).getBytes());
				String hash = MD5.md5(userString + "|" + GriefBot.getInstance().getSeed());
				webSocket.send("AUTHORIZE|" + hash);
			} else {
				webSocket.send("UNAUTHORIZED");
			}
		} else {
			webSocket.send("AUTHORIZE|" + null);
		}
	}

}
