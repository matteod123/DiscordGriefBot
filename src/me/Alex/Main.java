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
	
	// BOTInvite: https://discord.com/oauth2/authorize?client_id=832606240583450674&scope=bot&permissions=1006633022

	public ShardManager manager;
	public static Main instance;
	
	public String trustcmd = "%trust";
	//private String encodedToken = "NGY0NDRkNzk0ZTZhNDEzMjRkNmE1MTc3NGU1NDY3N2E0ZTQ0NTU3NzRlNmE2MzMwMmU1OTQ4NmQ0ZjVmNDEyZTU3MzA0YjQ4MzU2ZjVhNzU3YTQ5NDk2NzU2NTg3MTQxNzk3NzJkNGE3MzQ3Mzc0ODZhMzE2Nw==";
	
	private ArrayList<User> trusted = new ArrayList<User>();
	public boolean maintance = false;
	
	public ArrayList<User> getTrusted() {
		return this.trusted;
	}
	
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
					
					
					
				} else {
					System.out.println("Builder nicht definiert!");
				}
				
					
					reader.close();
				}
			} catch (IOException e) {}
			
		});
		return t;
		
	}
	
	public static String unHex(String arg) {        

	    String str = "";
	    for(int i=0;i<arg.length();i+=2)
	    {
	        String s = arg.substring(i, (i + 2));
	        int decimal = Integer.parseInt(s, 16);
	        str = str + (char) decimal;
	    }       
	    return str;
	}
	
	public static String decodeBase64(String encoded) {
		byte[] decodedBytes = Base64.getDecoder().decode(encoded);
		String decodedString = new String(decodedBytes);
		return decodedString;
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
