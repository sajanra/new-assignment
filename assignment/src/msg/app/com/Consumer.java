package msg.app.com;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.*;

public class Consumer implements Runnable {
    private static final Logger logger = Logger.getLogger(Consumer.class.getName());
    private final MessageQueue queue;

    public Consumer(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
    	Connection connection = null;
        try{
        	connection = queue.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queue.getQueueName());
            MessageConsumer consumer = session.createConsumer(destination);

            while (true) {
                Message message = consumer.receive();
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    logger.log(Level.INFO, "number of consumed messages: {0}", textMessage.getText());
                }
            }
        } catch (JMSException e) {
            logger.log(Level.SEVERE, "error in consumer", e);
        }finally {
        	try {
				connection.close();
			} catch (JMSException e) {
				logger.log(Level.SEVERE, "error in consumer while closing the connection", e);
			}
		}
    }
}