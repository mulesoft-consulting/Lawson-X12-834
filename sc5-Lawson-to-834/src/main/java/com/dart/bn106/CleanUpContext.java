package com.dart.bn106;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CleanUpContext {
	
    static public final Properties prop = new Properties();
	
	static {
		InputStream input = null;
		
		try {

			input = new FileInputStream("src/main/resources/bn106.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			System.out.println(prop.getProperty("STA0"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private CleanUpStrategy strategy;
	  //this can be set at runtime by the application preferences
	  public void setCleanUpStrategy(CleanUpStrategy strategy) {
	    this.strategy = strategy;
	  }
	  
	  //use the strategy
	  public String cleanupLine(String line) {
	    return strategy.cleanupLine(line);
	  }
}
