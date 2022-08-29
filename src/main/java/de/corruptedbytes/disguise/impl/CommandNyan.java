package de.corruptedbytes.disguise.impl;

import de.corruptedbytes.disguise.Command;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class CommandNyan extends Command {

	@Override
	public String getCommand() {
		return "nyan";
	}

	@Override
	public String getAppendix() {
		return null;
	}

	@Override
	public void onCommand(String command, String[] args, Message message, User sender, Guild guild) throws Exception {
		message.getChannel().sendMessage("https://tenor.com/view/nyan-cat-rainbow-cat-kitten-kitty-gif-5716621").queue();
	}

}
