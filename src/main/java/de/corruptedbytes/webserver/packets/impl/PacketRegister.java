package de.corruptedbytes.webserver.packets.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.java_websocket.WebSocket;

import de.corruptedbytes.logger.GriefBotLogger;
import de.corruptedbytes.logger.GriefBotLoggerLevel;
import de.corruptedbytes.utils.Config;
import de.corruptedbytes.webserver.packets.Packet;

public class PacketRegister extends Packet {

	@Override
	public String getPacket() {
		return "REGISTER";
	}

	@Override
	public void onPacketReceive(WebSocket webSocket, String message) throws Exception {
		if (!Config.USER_FILE.exists()) {
			String user = new String(Base64.getDecoder().decode(message), StandardCharsets.UTF_8);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(Config.USER_FILE, false));
		    writer.write(user);
		    writer.close();
		} else {
			GriefBotLogger.log("[USER/REGISTER] A user is already registred!", GriefBotLoggerLevel.ERROR);
		}
	}

}
