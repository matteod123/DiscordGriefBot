package me.Alex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.security.auth.login.LoginException;
import listener.CommandListener;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class Main extends ListenerAdapter {
	
	public ShardManager manager;
	public static Main instance;
	
	public static void main(String[] args) throws LoginException, IllegalArgumentException {
		new Main();
	}
	
	public Main() {
		DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(read());
		
		builder.addEventListeners(new CommandListener());
		builder.addEventListeners(this);
		builder.setActivity(Activity.streaming("UTILS", "https://www.twitch.tv/twitch"));
		builder.setStatus(OnlineStatus.ONLINE);
		consoleCMD().start();
		try {
			manager = builder.build();
		} catch (LoginException | IllegalArgumentException e) {
			System.out.println("BOT kann sich mit discord.com nicht authentizieren!");
		}
		
		
	}
	
	public static String read() {
		StringBuilder builder = new StringBuilder();
	 try {
	      File myObj = new File("token.yml");
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	        builder.append(data);
	      }
	      myReader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("Fehler: Konnte 'token.yml' nicht finden.\nDas System konnte den angegebenen Pfad nicht finden.\n\n");
	    }
	return builder.toString();
	}
	
	@Override
	public void onReady(ReadyEvent e){
		instance = this;
		System.out.println("GriefBOT wurde gestartet!");
	    } 
	
	public Thread consoleCMD() {
		
		Thread t = new Thread(() -> {
			
			String line = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			try {
				while((line = reader.readLine()) != null) {
					
					if (manager != null) {
					if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("stop") || line.equalsIgnoreCase("end")) {
							manager.setStatus(OnlineStatus.OFFLINE);
							manager.shutdown();
							System.out.println("Bot wurde heruntergefahren!");
						} 		
				} 
				else 
				{
					System.out.println("Builder nicht definiert!");
				}
				
					
					reader.close();
				}
			} catch (IOException e) {}
			
		});
		return t;
		
	}

	private static final ScheduledExecutorService scheduledThreadPoolExecutor = Executors.newScheduledThreadPool(10);
    
    public static void doPause(int ms) {
        try {
            scheduledThreadPoolExecutor.schedule(() -> {
            }, ms, TimeUnit.MILLISECONDS).get();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
