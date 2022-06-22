package de.corruptedbytes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GriefBotLogger {
	
	private static String getFormattedDate() {
		LocalDateTime date = LocalDateTime.now();
	    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String formattedDate = date.format(format);
	    return formattedDate;
	}
	
	public static void log(Object log) {
	    System.out.println("[" + getFormattedDate() + "] " + log);
	}
	
	public static void log(Object log, boolean newline) {
	     if (newline)
	    	 System.out.println("[" + getFormattedDate() + "] " + log);
	     else
	    	 System.out.print("[" + getFormattedDate() + "] " + log);
	}

}
