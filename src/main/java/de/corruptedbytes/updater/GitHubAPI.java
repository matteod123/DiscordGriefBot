package de.corruptedbytes.updater;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.corruptedbytes.GriefBot;

public class GitHubAPI {

	private final static String GITHUB_API_URL = "https://api.github.com/repos/CorruptedBytes/DiscordGriefBot/releases";

	public JSONObject getReleaseJSON() throws JSONException, IOException {
		return new JSONObject(new JSONArray(getWebContent(GITHUB_API_URL)).get(0).toString());
	}

	public String getLatestVersion() throws JSONException, IOException {
		return getReleaseJSON().getString("tag_name");
	}

	public String getLatestDownloadURL() throws JSONException, IOException {
		return new JSONObject(new JSONArray(getReleaseJSON().getJSONArray("assets")).get(0).toString())
				.getString("browser_download_url");
	}

	private String getWebContent(String webUrl) throws IOException {
		URL url = new URL(webUrl);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("Connection", "close");
		conn.setRequestProperty("User-Agent",
				"DiscordGriefBot/" + GriefBot.getInstance().getVersion().replaceAll("v", ""));
		return new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
	}

	public void downloadFileFromURL(String urlString, File destination) {
		try {
			URL website = new URL(urlString);
			ReadableByteChannel rbc;
			rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(destination);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			rbc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
