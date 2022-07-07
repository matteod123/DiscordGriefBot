package de.corruptedbytes;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.security.auth.login.LoginException;

import de.corruptedbytes.logger.GriefBotLogger;
import de.corruptedbytes.logger.GriefBotLoggerLevel;
import de.corruptedbytes.utils.Config;
import de.corruptedbytes.utils.Registry;
import de.corruptedbytes.utils.Shelv;
import de.corruptedbytes.webserver.WebServer;
import de.corruptedbytes.webserver.WebServerIndex;
import de.corruptedbytes.webserver.WebServerSocket;
import de.corruptedbytes.webserver.webserverindexes.FileIndexer;
import de.corruptedbytes.webserver.webserverindexes.sites.Main;
import de.corruptedbytes.webserver.webserverindexes.sites.Panel;
import de.corruptedbytes.webserver.webserverindexes.sites.Settings;
import de.corruptedbytes.webserver.webserverindexes.sites.Setup;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bootstrap {

	public static void main(String[] args) {
		System.out.println("\r\n" + new String(Base64.getDecoder().decode(Shelv.ASCII_BANNER), StandardCharsets.UTF_8));
		System.out.println("\r\n=-=-=-=-=-= Discord Grief-Bot by CorruptedBytes [" + GriefBot.getInstance().getVersion() + "] =-=-=-=-=-=\r\n");
		
		try {
			Config.initServerConfig();
			initWebServer();
			
			if (Config.CONFIG_FILE.exists())
				initDiscordBot();
			
		} catch (Exception e) {
			GriefBotLogger.log("[Bootstrap] " + e.getMessage(), GriefBotLoggerLevel.ERROR);
		}
	}

	public static void initDiscordBot() throws IOException {
		Config.initConfig();
		
		DefaultShardManagerBuilder builder = DefaultShardManagerBuilder
				.createDefault(GriefBot.getInstance().getDiscordBotToken()).setMemberCachePolicy(MemberCachePolicy.ALL)
				.setChunkingFilter(ChunkingFilter.ALL).enableIntents(GatewayIntent.GUILD_MEMBERS);

		builder.addEventListeners(new GriefBotListener());
		builder.setActivity(Activity.streaming(GriefBot.getInstance().getActivityDescription(), "https://www.twitch.tv/twitch"));
		builder.setStatus(OnlineStatus.ONLINE);

		try {
			GriefBot.getInstance().botManager = builder.build();
		} catch (LoginException | IllegalArgumentException e) {
			GriefBotLogger.log("[Bootstrap] " + e.getMessage(), GriefBotLoggerLevel.ERROR);
		}
	}

	public static void initWebServer() throws IOException {
		Registry<WebServerIndex> webServerIndexRegistry = new Registry<WebServerIndex>();
		
		webServerIndexRegistry.register(new FileIndexer());
		webServerIndexRegistry.register(new Main());
		webServerIndexRegistry.register(new Setup());
		webServerIndexRegistry.register(new Panel());
		webServerIndexRegistry.register(new Settings());
		
		GriefBot.getInstance().setWebSocketServerListener(new WebServerSocket(GriefBot.getInstance().getWebSocketServer()));
		GriefBot.getInstance().getWebSocketServerListener().start();
		
		WebServer webServer = new WebServer(new InetSocketAddress(GriefBot.getInstance().getWebServerPort()), webServerIndexRegistry);
		webServer.start();
	}

}
