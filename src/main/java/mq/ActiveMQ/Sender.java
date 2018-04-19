package mq.ActiveMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息发送者
 * 
 * @author weihang 2018年4月12日
 */
public class Sender {

	private final static int num = 5;

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;// 连接工厂
		Connection connection = null;// 连接
		Session session;// 消息进程
		Destination destination;// 消息目的地
		MessageProducer produce;// 消息发送者
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");// 建立连接工厂
		try {
			connection = connectionFactory.createConnection();// 从连接工厂获取连接
			connection.start();//
			/*AUTO_ACKNOWLEDGE:当客户端从 receive 或 onMessage成功返回时，Session 自动签收客户端的这条消息的收条
			 *CLIENT_ACKNOWLEDGE:需要调用acknowledge方法来接收 */
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("hello");// 设置消息队列,发送者跟消费者要相同才能相互传递消息
//			destination = session.createTopic("test");
			produce = session.createProducer(destination);
			produce.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			// 发送业务消息
			sendMessage(session, produce, "test");
			session.commit();
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

	private static void sendMessage(Session session, MessageProducer produce, String name) throws JMSException {
		TextMessage message = session.createTextMessage("i am message number" + name);
		produce.setTimeToLive(1000);//设置保留时间
		produce.send(message);
	}

}
