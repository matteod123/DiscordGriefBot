package de.corruptedbytes.disguise;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public abstract class Command {
	
	public abstract String getCommand();

	public abstract String getAppendix();

	public abstract void onCommand(String command, String[] args, Message message, User sender, Guild guild)
			throws Exception;

}
