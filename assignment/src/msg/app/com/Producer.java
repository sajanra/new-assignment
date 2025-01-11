package msg.app.com;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer implements Runnable{
	 	private static final Logger logger = Logger.getLogger(Producer.class.getName());
	    private final String brokerUrl;
	    private final String queueName;
	    private final int numberOfMessages;

	    public Producer(String brokerUrl, String queueName, int numberOfMessages) {
	        this.brokerUrl = brokerUrl;
	        this.queueName = queueName;
	        this.numberOfMessages = numberOfMessages;
	    }

	    @Override
	    public void run() {
	    	ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
	    	Connection connection = null;
	    	try{
	    		connection = connectionFactory.createConnection();
	            connection.start();
	            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	            Destination destination = session.createQueue(queueName);
	            MessageProducer producer = session.createProducer(destination);

	            for (int i = 0; i < numberOfMessages; i++) {
	                String messageText = "message " + i;
	                TextMessage message = session.createTextMessage(messageText);
	                producer.send(message);
	                logger.log(Level.INFO, "number of produced messages: {0}", messageText);
	                Tracker.successCount();
	            }

	        } catch (JMSException e) {
	            Tracker.failureCount();
	            logger.log(Level.SEVERE, "error in producer", e);
	        }finally {
				try {
					connection.close();
				} catch (JMSException e) {
					logger.log(Level.SEVERE, "error in producer while closing the connection ", e);
				}
			}
	    }
	    
}
