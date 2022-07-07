package de.corruptedbytes.disguise;

import java.util.concurrent.CopyOnWriteArrayList;

import de.corruptedbytes.disguise.impl.CommandMeme;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class CommandManager {
	
	private CopyOnWriteArrayList<Command> commands = new CopyOnWriteArrayList<>();
	
	public CommandManager() {
		getCommands().add(new CommandMeme());
	}
	
	public CopyOnWriteArrayList<Command> getCommands() {
		return commands;
	}
	
	public void reload() {
		commands.clear();
		new CommandManager();
	}
	
	public void callCommand(String input, Message message, User sender, Guild guild) {
		String[] split = input.split(" ");
		String command = split[0];
		String args = input.substring(command.length()).trim();
		for (Command c : getCommands()) {
			if (c.getCommand().equalsIgnoreCase(command)) {
				try {
					c.onCommand(args, args.split(" "), message, sender, guild);
				} catch(Exception e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}

}
