package de.corruptedbytes.webserver;

import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import de.corruptedbytes.GriefBot;
import de.corruptedbytes.GriefBotLogger;

public class WebServerSocket extends WebSocketServer {

	private InetSocketAddress address;
	
	public WebServerSocket(InetSocketAddress address) {
		super(address);
		this.address = address;
	}
	
	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		GriefBot.getInstance().getClients().add(conn);
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		GriefBot.getInstance().getClients().remove(conn);
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		GriefBot.getInstance().getPacketManager().callPacket(conn, message);
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		ex.printStackTrace();
	}

	@Override
	public void onStart() {
		GriefBotLogger.log("[WebSocket Server] Running on port " + address.getPort());
	}

}
