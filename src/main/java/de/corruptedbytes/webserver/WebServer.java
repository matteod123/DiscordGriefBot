package de.corruptedbytes.webserver;

import com.sun.net.httpserver.HttpServer;

import de.corruptedbytes.logger.GriefBotLogger;
import de.corruptedbytes.logger.GriefBotLoggerLevel;
import de.corruptedbytes.utils.ConnectionUtils;
import de.corruptedbytes.utils.Registry;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class WebServer {

	private final HttpServer httpServer;
	private final InetSocketAddress host;
	private final Registry<WebServerIndex> webServerIndexRegistry;

	public WebServer(InetSocketAddress host, Registry<WebServerIndex> registry) throws IOException {
		this.host = host;
		this.httpServer = HttpServer.create(host, 0);
		this.webServerIndexRegistry = registry;
	}

	public void start() throws UnknownHostException {
		this.webServerIndexRegistry.getRegistered().forEach(webServerIndex -> {
			try {
				webServerIndex.index(this.httpServer);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		this.httpServer.start();
		GriefBotLogger.log("[Web Server] Running on http://" + ConnectionUtils.lookupLocalAddress() + ":" + host.getPort(), GriefBotLoggerLevel.INFO);
	}

	public final HttpServer getHttpServer() {
		return this.httpServer;
	}

	public final Registry<WebServerIndex> getWebServerIndexRegistry() {
		return this.webServerIndexRegistry;
	}
}
