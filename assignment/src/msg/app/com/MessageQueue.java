package msg.app.com;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;


public class MessageQueue {
    private final String brokerUrl;
    private final String queueName;

    public MessageQueue(String brokerUrl, String queueName) {
        this.brokerUrl = brokerUrl;
        this.queueName = queueName;
    }

    public Connection createConnection() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        return connectionFactory.createConnection();
    }

    public String getQueueName() {
        return queueName;
    }
   
}