package de.corruptedbytes.disguise.commands.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONObject;

import de.corruptedbytes.disguise.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class CommandMeme extends Command {

	private final String API_ENDPOINT = "https://meme-api.herokuapp.com/gimme";

	@Override
	public String getCommand() {
		return "meme";
	}

	@Override
	public String getAppendix() {
		return null;
	}

	@Override
	public void onCommand(String command, String[] args, Message message, User sender, Guild guild) throws Exception {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Meme");
		builder.setImage(getMeme());
		message.getChannel().sendMessage(builder.build()).queue();
	}

	private String getMeme() throws Exception {
		String parseLine;
		String jsonString = "";
		URL URL = new URL(API_ENDPOINT);
		BufferedReader br = new BufferedReader(new InputStreamReader(URL.openStream()));

		while ((parseLine = br.readLine()) != null) {
			jsonString += parseLine;
		}
		br.close();
		
		JSONObject jsonObject = new JSONObject(jsonString);
		return jsonObject.getString("url");
	}
}
