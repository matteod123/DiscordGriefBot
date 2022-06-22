package de.corruptedbytes.webserver;

import com.sun.net.httpserver.HttpExchange;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class WebServerUtil {
	
	public static void sendFile(HttpExchange exchange, int statusCode, File file) throws IOException {
		sendResponse(exchange, statusCode, Files.readAllBytes(file.toPath()));
	}

	public static void sendResponse(HttpExchange exchange, int statusCode, byte[] bytes) throws IOException {
		exchange.sendResponseHeaders(statusCode, 0L);
		OutputStream responseBody = exchange.getResponseBody();
		responseBody.write(bytes);
		responseBody.close();
		exchange.close();
	}

	public static void redirect(HttpExchange exchange, String location) throws IOException {
		exchange.getResponseHeaders().add("Location", location);
		exchange.sendResponseHeaders(302, 0L);
		exchange.close();
	}
}
