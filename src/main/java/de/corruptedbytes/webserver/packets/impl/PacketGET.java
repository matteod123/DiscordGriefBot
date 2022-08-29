package de.corruptedbytes.webserver.packets.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import org.java_websocket.WebSocket;
import org.json.JSONArray;
import org.json.JSONObject;

import de.corruptedbytes.GriefBot;
import de.corruptedbytes.utils.Config;
import de.corruptedbytes.utils.FileUtils;
import de.corruptedbytes.webserver.packets.Packet;
import net.dv8tion.jda.api.entities.Guild;

public class PacketGET extends Packet {

	@Override
	public String getPacket() {
		return "GET";
	}

	@Override
	public void onPacketReceive(WebSocket webSocket, String message) throws Exception {
		if (message.toLowerCase().startsWith("config")) {
			webSocket.send("CONFIG|" + FileUtils.readFile(Config.CONFIG_FILE));
		}
		
		if (message.toLowerCase().startsWith("id")) {
			webSocket.send("ID|" + new String(Base64.getDecoder().decode(GriefBot.getInstance().getDiscordBotToken().split("\\.")[0]), StandardCharsets.UTF_8));
		}

		if (message.toLowerCase().startsWith("guilds")) {
			if (GriefBot.getInstance().getGuilds() != null) {
				JSONObject jsonObject = new JSONObject();
				
				for (int i = 0; i < GriefBot.getInstance().getGuilds().size(); i++) {
					List<Guild> guild = GriefBot.getInstance().getGuilds();
					JSONArray jsonArray = new JSONArray();
					JSONObject item = new JSONObject();
					
					item.put("id", guild.get(i).getId());
					item.put("name", guild.get(i).getName());
					item.put("avatar", guild.get(i).getIconUrl());
					item.put("owner", guild.get(i).getOwner().getUser().getName());
					
					jsonArray.put(item);
					jsonObject.put(String.valueOf(i), jsonArray);
				}
				webSocket.send("GUILDS|" + Base64.getEncoder().encodeToString(jsonObject.toString().getBytes(StandardCharsets.UTF_8)));
			}
		}
	}

}
