package com.justa.test.aws.message;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsSender {

	public static final String DEFAULT_QUEUE_NAME = "input";

	public static void main(String args[]) throws JMSException {

		Connection connection  = AwsSqsFactory.getJmsConnectionn();
		// Create the session
		Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		Destination dest = 	session.createQueue(DEFAULT_QUEUE_NAME);
		MessageProducer producer = session.createProducer(dest);

		connection.start();

		sendMessages(session, producer);

		// Close the connection. This closes the session automatically
		connection.close();
		System.out.println("Connection closed");
	}

	private static void sendMessages(Session session, MessageProducer producer) {		
		
		try {
			System.out.println("sending a message...");
			TextMessage  msg = session.createTextMessage();
			msg.setText("From JmsSender at " + new Date());
			producer.send(msg);

			System.out.println("send out a message " );


		} catch (JMSException e) {
			System.err.println("Error receiving from SQS: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
