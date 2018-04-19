package mq.ActiveMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Test {

	public static void main(String[] args) {
		ConnectionFactory connectionFaction;//连接工厂
		Connection conn = null;//连接
		Session session;//消息进程
		Destination destination;
		MessageProducer produce;
		connectionFaction = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
		try {
			conn = connectionFaction.createConnection();
			conn.start();
			session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("hello");
			produce = session.createProducer(destination);
			massage(session, produce, "test");
			session.commit();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (null != conn) {
				try {
					conn.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void massage(Session session, MessageProducer produce, String name) {
		try {
			TextMessage msg = session.createTextMessage(name);
			produce.send(msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
