package com.justa.test.aws.message;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;

// it comes from https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-sqs-messages.html
public class SendSQSMessages {

	private static final String QUEUE_NAME = "input" ;
	public static final String SchemaVersion = "SchemaVersion" ;
	public static final String OperationType = "OperationType" ;
	
	private final static Map<String, MessageAttributeValue> MessageAttributes = new HashMap<>();
	static {
		MessageAttributeValue operation = new MessageAttributeValue().withStringValue("DATA_SYNC");		
		operation.setDataType("String");
		MessageAttributeValue schemaVersion = new MessageAttributeValue().withStringValue("1.0");
		schemaVersion.setDataType("String");
		MessageAttributes.put(SchemaVersion, schemaVersion);
		MessageAttributes.put(OperationType, operation);
	}
	
	
    public static void main(String[] args)    {
    	
    	// we can use the following way to control accessKey programmatically 
//		Properties props = System.getProperties();
//		props.setProperty("aws.accessKeyId", "AKIA44WGAELBKZPOLJ6T");
//		props.setProperty("aws.secretKey", "mySecretyKey");
//		props.setProperty("aws.region", "us-east-1");
    	
    	// AWS default client will use this order to get assessKey (1) environment (2)  Java system properties (3) .aws/credential ... 
    	
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        
        String queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
     // receive messages from the queue
        
        String msgBody = "Justin test from sendSQSMessages at " + new Date();
        
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
        		.withQueueUrl(queueUrl)                
                .withMessageBody(msgBody)
                .withMessageAttributes(MessageAttributes);
        
        System.out.println("start to send message..." + new Date());
        
        sqs.sendMessage(sendMessageRequest);
        
        System.out.println("end at send message..." + new Date());

    }

}
