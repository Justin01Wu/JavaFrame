package com.justa.test.aws.message;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

// it comes from https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-sqs-messages.html
public class ReceiveAndDeleteSQSMessages {

	private static final String QUEUE_NAME = "input" ;
	
    public static void main(String[] args)    {
    	
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        // must set region = us-east-1 in C:\Users\USERNAME\.aws\config
        
        String queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
     // receive messages from the queue
        
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl)
        		.withAttributeNames(SendSQSMessages.SchemaVersion, SendSQSMessages.OperationType)
        		.withWaitTimeSeconds(10)     // long poll: wait 10 seconds if didn't get msg immediately, max is 20 seconds
        		.withMaxNumberOfMessages(10);
        
        System.out.println("start to get message..." + new Date());
        
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        if(messages.isEmpty()){
        	System.out.println("didn't get any message");
        }else{
            for (Message m : messages) {
            	System.out.println("msgId: " + m.getMessageId());
            	System.out.println("body: " + m.getBody());
            	
            	Map<String, MessageAttributeValue> attributes = m.getMessageAttributes();
            	MessageAttributeValue att = attributes.get(SendSQSMessages.SchemaVersion);
            	if( att!= null) {
            		System.out.println(SendSQSMessages.SchemaVersion + ": " + att.getStringValue());
            	}
            	
            	att = attributes.get(SendSQSMessages.OperationType);
            	if( att!= null) {
            		System.out.println(SendSQSMessages.OperationType + ": " + att.getStringValue());
            	}

            	String receipt  = m.getReceiptHandle();
            	sqs.changeMessageVisibility(queueUrl, receipt, 120);  //default is 30 seconds
            	// I need more time to handle this message, so adjust Visibility time out for more time
            	// do some heavy process
            	System.out.println("processing...");
            	
                sqs.deleteMessage(queueUrl, receipt);
            }
        }
        
        System.out.println("end at get message..." + new Date());

    }

}
