package de.corruptedbytes.disguise.impl;

import de.corruptedbytes.disguise.Command;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class CommandPing extends Command {

	@Override
	public String getCommand() {
		return "ping";
	}

	@Override
	public String getAppendix() {
		return null;
	}

	@Override
	public void onCommand(String command, String[] args, Message message, User sender, Guild guild) throws Exception {
		message.getChannel().sendMessage("Pong!").queue();
	}

}
