package mq.ActiveMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session;
		Destination destination;
		MessageConsumer consumer;
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("hello");
//			destination = session.createTopic("test");
			consumer = session.createConsumer(destination);
			//简历监听,传递对象时需要序列化该对象
//			consumer.setMessageListener(new MessageListener() {
//
//				public void onMessage(Message message) {
//					try {
//                        //同样的，强转为ObjectMessage，然后拿到对象，强转为Person
//                        Person p = (Person) ((ObjectMessage)message).getObject();
//                        System.out.println(p);
//                    } catch (JMSException e) {
//                        e.printStackTrace();
//                    }
//					
//				}
//				
//			});
			if (true) {
				
			}
			
			while (true) {
				TextMessage message = (TextMessage) consumer.receive(30*1000);
//				consumer.getMessageListener().onMessage(message);
				if (null != message) {
					System.out.println("shoudao " + message.getText());
				} else {
					System.out.println("none");
					break;
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != connection) {
					connection.close();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
