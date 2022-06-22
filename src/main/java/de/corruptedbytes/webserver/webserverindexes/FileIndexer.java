package de.corruptedbytes.webserver.webserverindexes;

import com.sun.net.httpserver.HttpServer;

import de.corruptedbytes.utils.Config;
import de.corruptedbytes.webserver.WebServerIndex;
import de.corruptedbytes.webserver.WebServerUtil;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FileIndexer implements WebServerIndex {

	public void index(HttpServer httpServer) throws IOException {
		File directory = new File(Config.WEB_DIRECTORY + "/public");
		for (File file : (File[]) Objects.<File[]>requireNonNull(directory.listFiles()))
			indexFile(file, httpServer, "");
	}

	public void indexFile(File file, HttpServer httpServer, String root) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				indexFile(f, httpServer, String.format("%s/%s", new Object[] { root, file.getName() }));
			}
		} else {
			httpServer.createContext(String.format("%s/%s", new Object[] { root, file.getName() }),
					exchange -> WebServerUtil.sendFile(exchange, 200, file));
		}
	}
}
