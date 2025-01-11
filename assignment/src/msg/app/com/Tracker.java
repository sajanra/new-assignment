package msg.app.com;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Tracker {
	  private static final Logger logger = Logger.getLogger(Tracker.class.getName());
	 
	  private static int successCount = 0;
      private static int failureCount = 0;
      
      public static synchronized void successCount() {
          successCount++;
      }

      public static synchronized void failureCount() {
    	  failureCount++;
      }

      public static void printStats() {
        logger.log(Level.INFO, "total success messages: {0}",successCount);
        logger.log(Level.INFO, "total failure messages: {0}", failureCount);
      }

}
