package de.corruptedbytes.webserver.webserverindexes.sites;

import java.io.File;
import java.io.IOException;

import com.sun.net.httpserver.HttpServer;

import de.corruptedbytes.utils.Config;
import de.corruptedbytes.webserver.WebServerIndex;
import de.corruptedbytes.webserver.WebServerUtil;

public class Settings implements WebServerIndex {

	@Override
	public void index(HttpServer httpServer) throws IOException {
		httpServer.createContext("/settings", exchange -> {
			if (!exchange.getRequestMethod().equals("GET")) {
				exchange.close();
				return;
			}
			
			if (!Config.CONFIG_FILE.exists()) {
				WebServerUtil.redirect(exchange, "/setup");
			} else {
				WebServerUtil.sendFile(exchange, 200, new File(Config.WEB_DIRECTORY + "/settings.html"));
			}
		});
	}

}
