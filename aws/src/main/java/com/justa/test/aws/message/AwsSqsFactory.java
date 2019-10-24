package com.justa.test.aws.message;

import javax.jms.JMSException;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

public class AwsSqsFactory {
	
	
	public static SQSConnection getJmsConnectionn() throws JMSException{
	
		// Create the connection factory based on the config
		SQSConnectionFactory connectionFactory = new SQSConnectionFactory(new ProviderConfiguration(),
				AmazonSQSClientBuilder.standard());

		// Create the connection
		SQSConnection connection = connectionFactory.createConnection();

		return connection;
	}

}
