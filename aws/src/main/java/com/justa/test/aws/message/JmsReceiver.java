package com.justa.test.aws.message;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

// from https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/prerequisites.html
// use JMS to get message from AWS SQS

public class JmsReceiver {

	public static final String DEFAULT_QUEUE_NAME = "input";

	public static void main(String args[]) throws JMSException {

		Connection connection  = AwsSqsFactory.getJmsConnectionn();
		
		// Create the session
		Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);  
		// client side must explicitly acknowledge the message
		
		MessageConsumer consumer = session.createConsumer(session.createQueue(DEFAULT_QUEUE_NAME));

		connection.start();

		receiveMessages(session, consumer);

		// Close the connection. This closes the session automatically
		connection.close();
		System.out.println("Connection closed");
	}

	private static void receiveMessages(Session session, MessageConsumer consumer) {
		try {
			while (true) {
				System.out.println("Waiting for messages");
				// Wait 1 minute for a message
				Message message = consumer.receive(TimeUnit.SECONDS.toMillis(20));
				if (message == null) {
					System.out.println("Shutting down after 20 seconds of silence");
					break;
				}
				System.out.println("message: expired on " + new Date(message.getJMSExpiration()));
				message.acknowledge();
				System.out.println("Acknowledged message: " + message.getJMSMessageID());
				String msgBody = ((TextMessage) message).getText();
				System.out.println("Acknowledged message: " + msgBody);

			}
		} catch (JMSException e) {
			System.err.println("Error receiving from SQS: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
