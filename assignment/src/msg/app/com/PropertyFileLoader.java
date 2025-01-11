package msg.app.com;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileLoader {
	
	 private static Properties properties = new Properties();

	    static {
	        try {
	            properties.load(new FileInputStream("resources/config.properties"));
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("failed to load config.properties file", e);
	        }
	    }
	    
	    public static String getProperty(String key) {
	        return properties.getProperty(key);
	    }
}
