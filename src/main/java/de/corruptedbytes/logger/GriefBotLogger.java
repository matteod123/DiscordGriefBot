package de.corruptedbytes.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GriefBotLogger {
	
	private static String getFormattedDate() {
		LocalDateTime date = LocalDateTime.now();
	    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String formattedDate = date.format(format);
	    return formattedDate;
	}
	
	public static void log(Object log, GriefBotLoggerLevel level) {
	    System.out.println("[" + getFormattedDate() + "] [" + level.name().toUpperCase() + "] " + log);
	}
}