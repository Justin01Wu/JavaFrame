package com.justa.test.aws.message;

import java.util.Date;
import java.util.List;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
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
        		  .withWaitTimeSeconds(10)     // long poll: wait 10 seconds, max is 20 seconds
        		  .withMaxNumberOfMessages(10);
        
        System.out.println("start to get message..." + new Date());
        
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        if(messages.isEmpty()){
        	System.out.println("didn't get any message");
        }else{
            for (Message m : messages) {
            	System.out.println(m.getMessageId());
            	System.out.println(m.getBody());
                sqs.deleteMessage(queueUrl, m.getReceiptHandle());
            }
        }
        
        System.out.println("end at get message..." + new Date());

    }

}
