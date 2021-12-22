package me.corruptedbytes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utils {
	
	public static String readTokenFile() {
		StringBuilder builder = new StringBuilder();
		try {
	      File obj = new File("token.yml");
	      Scanner reader = new Scanner(obj);
	      while (reader.hasNextLine()) {
	        String data = reader.nextLine();
	        builder.append(data);
	      }
	      reader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
		return builder.toString();
	}

}
