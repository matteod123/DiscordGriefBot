package de.corruptedbytes.webserver.webserverindexes.sites;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpServer;

import de.corruptedbytes.GriefBot;
import de.corruptedbytes.utils.Config;
import de.corruptedbytes.webserver.WebServerIndex;
import de.corruptedbytes.webserver.WebServerUtil;

public class WebSocket implements WebServerIndex {

	@Override
	public void index(HttpServer httpServer) throws IOException {
		httpServer.createContext("/websocket", exchange -> {
			if (!exchange.getRequestMethod().equals("GET")) {
				exchange.close();
				return;
			}
			
			if (Config.SERVER_FILE.exists()) {
				WebServerUtil.sendResponse(exchange, 200, (GriefBot.getInstance().getWebSocketServer().getAddress().getHostAddress() + ":" + GriefBot.getInstance().getWebSocketServer().getPort()).getBytes(StandardCharsets.UTF_8));
			} else {
				WebServerUtil.sendResponse(exchange, 200, ("null").getBytes(StandardCharsets.UTF_8));
			}
		});
	}
}
