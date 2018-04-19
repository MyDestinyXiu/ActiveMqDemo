package mq.ActiveMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * 消息接受者
 * @author weihang
 * 2018年4月12日
 */
public class Receiver {
	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session;
		Destination destination;
		MessageConsumer consumer;//消费者
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);//设置为true即消费消息后不清除
			destination = session.createQueue("hello");
//			destination = session.createTopic("test");
			consumer = session.createConsumer(destination);
			while (true) {
				TextMessage message = (TextMessage) consumer.receive(30*1000);//设置消费者消费时间
				if (null != message) {
//					message.acknowledge();
					System.out.println("shoudao "+message.getText());
				} else {
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
