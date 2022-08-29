package de.corruptedbytes.updater;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;

import de.corruptedbytes.GriefBot;

public class AutoUpdater extends GitHubAPI {
	
	public static void update() throws JSONException, IOException {
		AutoUpdater autoUpdater = new AutoUpdater();
		File currentFile = new File(System.getProperty("java.class.path"));
		if (autoUpdater.isUpdateAviable()) {
			autoUpdater.downloadFileFromURL(autoUpdater.getLatestDownloadURL(), currentFile);
			
			System.out.println("");
			System.out.println("!!! NEW VERSION AVIABLE !!!");
			System.out.println("Please restart!");
			System.out.println("");
			System.exit(0);
		}
	}
	
	public boolean isUpdateAviable() throws JSONException, IOException {
		return !GriefBot.getInstance().getVersion().equals(getLatestVersion());
	}
	
}
