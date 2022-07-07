package de.corruptedbytes.utils;

import java.net.DatagramSocket;
import java.net.InetAddress;

import de.corruptedbytes.logger.GriefBotLogger;
import de.corruptedbytes.logger.GriefBotLoggerLevel;

public class ConnectionUtils {

	public static String lookupLocalAddress() {
		try {
			try (final DatagramSocket datagramSocket = new DatagramSocket()) {
			    datagramSocket.connect(InetAddress.getByName("8.8.8.8"), 12345);
			    return datagramSocket.getLocalAddress().getHostAddress();
			}
		} catch (Exception e) {
			GriefBotLogger.log("[IP-Lookup] IP-Lookup failed! (" + e.getMessage() + ")", GriefBotLoggerLevel.WARNING);
		}
		return "127.0.0.1";
	}

}
