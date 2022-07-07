package de.corruptedbytes.webserver.packets.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.java_websocket.WebSocket;

import de.corruptedbytes.GriefBot;
import de.corruptedbytes.logger.GriefBotLogger;
import de.corruptedbytes.logger.GriefBotLoggerLevel;
import de.corruptedbytes.Bootstrap;
import de.corruptedbytes.utils.Config;
import de.corruptedbytes.webserver.packets.Packet;

public class PacketSetup extends Packet {

	@Override
	public String getPacket() {
		return "SETUP";
	}

	@Override
	public void onPacketReceive(WebSocket webSocket, String message) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(Config.CONFIG_FILE, false));
	    writer.write(message);
	    writer.close();
	    Config.initConfig();
	    
	    if (GriefBot.getInstance().getBotManager() == null) 
	    	Bootstrap.initDiscordBot();
	    
	    GriefBotLogger.log("[" + getPacket() + "] Settings applied", GriefBotLoggerLevel.INFO);
	}

}
