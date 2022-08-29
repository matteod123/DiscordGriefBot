package de.corruptedbytes;

import java.util.Base64;

import de.corruptedbytes.utils.Config;
import de.corruptedbytes.utils.MD5;

public class User {
	
	private String username;
	private String password;
	private String hash;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public User(String hash) {
		this.hash = hash;
	}
	
	public User(User user, String hash) {
		this(user.getUsername(), user.getPassword());
		this.hash = hash;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean verifyUser() {
		try {
			User user = Config.getUser();
			
			if (user != null) {
				String encodedUsername = Base64.getEncoder().encodeToString(user.getUsername().toLowerCase().getBytes());
				String password = user.getPassword();
				String seed = GriefBot.getInstance().getSeed();
				String finalHash = MD5.md5(Base64.getEncoder().encodeToString((encodedUsername + ":" + password).getBytes()) + "|" + seed);
				
				if (hash.trim().equals(finalHash))
					return true;
				else
					return false;
				
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
