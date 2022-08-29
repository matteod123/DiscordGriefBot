package de.corruptedbytes.webserver.packets.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.java_websocket.WebSocket;

import de.corruptedbytes.GriefBot;
import de.corruptedbytes.webserver.packets.Packet;
import net.dv8tion.jda.api.entities.TextChannel;

public class PacketSend extends Packet {

	@Override
	public String getPacket() {
		return "SEND";
	}

	@Override
	public void onPacketReceive(WebSocket webSocket, String message) throws Exception {
		String[] array = message.split("\\*");
		String channelID = new String(Base64.getDecoder().decode(array[0]), StandardCharsets.UTF_8);
		String msg = new String(Base64.getDecoder().decode(array[1]), StandardCharsets.UTF_8);
		
		TextChannel channel = GriefBot.getInstance().getBotManager().getTextChannelById(channelID);
		channel.sendMessage(msg).queue();
	}

}
