package de.corruptedbytes.webserver;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;

public interface WebServerIndex {
	
	void index(HttpServer paramHttpServer) throws IOException;
	
}
