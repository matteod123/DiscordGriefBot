package de.corruptedbytes.disguise;

import java.awt.Color;

import de.corruptedbytes.GriefBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class HelpManager {
	
	public static MessageAction getHelp(MessageChannel messageChannel) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(new Color(0xa64dff));
		
		eb.setDescription("**Help - Commands (" + GriefBot.getInstance().getCommandManager().getCommands().size() + ")**");
		
		for (int i = 0; i < GriefBot.getInstance().getCommandManager().getCommands().size(); i++) {
			if (GriefBot.getInstance().getCommandManager().getCommands().get(i).getAppendix() != null) {
        		eb.addField("**" + GriefBot.getInstance().getDisguiseCommandPrefix() + GriefBot.getInstance().getCommandManager().getCommands().get(i).getCommand() + " " + GriefBot.getInstance().getCommandManager().getCommands().get(i).getAppendix() + "**", "", false);
        	}
        	else if (GriefBot.getInstance().getCommandManager().getCommands().get(i).getAppendix() == null)
        	{
        		eb.addField("**" + GriefBot.getInstance().getDisguiseCommandPrefix() + GriefBot.getInstance().getCommandManager().getCommands().get(i).getCommand() + "**", "", false);
        	}
		}
		return messageChannel.sendMessage(eb.build());
	}
}
