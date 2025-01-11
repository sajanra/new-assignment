package msg.app.com;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class ProducerConsumerApp {
	
	private static final Logger logger = Logger.getLogger(ProducerConsumerApp.class.getName());

    public static void main(String[] args) {
    	
    	try {
			LogManager.getLogManager().readConfiguration(ProducerConsumerApp.class.getResourceAsStream("/logging.properties"));
			logger.info("going to send messages");
			sentToQueue();
    	}catch (IOException e) {
			logger.log(Level.SEVERE, " given file name is not exist ", e);
		}catch (Exception e) {
			logger.log(Level.SEVERE, " failed to sent messages in queue ", e);
		}
    	
    }
    
    public static void sentToQueue(){
    	String brokerUrl = PropertyFileLoader.getProperty("broker.url");
        String queueName = PropertyFileLoader.getProperty("queue.name");
        
        int numberOfMessages = 50;
        logger.info("sent messages in queue");
        MessageQueue queue = new MessageQueue(brokerUrl, queueName);
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(new Producer(brokerUrl, queueName, numberOfMessages));
        executorService.execute(new Consumer(queue));

        executorService.shutdown();
        
        Runtime.getRuntime().addShutdownHook(new Thread(Tracker::printStats));

        try {
            Thread.sleep(10000);  
            Tracker.printStats();  
        } catch (InterruptedException e) {
        	 logger.log(Level.SEVERE, "error in printStats method", e);
        }
    }
}