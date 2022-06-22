package de.corruptedbytes.webserver.webserverindexes.sites;

import java.io.IOException;

import com.sun.net.httpserver.HttpServer;

import de.corruptedbytes.utils.Config;
import de.corruptedbytes.webserver.WebServerIndex;
import de.corruptedbytes.webserver.WebServerUtil;

public class Main implements WebServerIndex {

	@Override
	public void index(HttpServer httpServer) throws IOException {
		httpServer.createContext("/", exchange -> {
			if (!exchange.getRequestMethod().equals("GET")) {
				exchange.close();
				return;
			}
			
			if (!Config.CONFIG_FILE.exists()) {
				WebServerUtil.redirect(exchange, "/setup");
			} else {
				WebServerUtil.redirect(exchange, "/panel");
			}
		});
	}
}
