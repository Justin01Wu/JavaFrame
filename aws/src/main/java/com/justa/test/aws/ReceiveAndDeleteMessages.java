package com.justa.test.aws;

import java.util.List;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;

// it comes from https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-sqs-messages.html
public class ReceiveAndDeleteMessages {

	private static final String QUEUE_NAME = "input" ;
	
    public static void main(String[] args)    {
    	
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        // must set region = us-east-1 in C:\Users\USERNAME\.aws\config
        
        String queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
     // receive messages from the queue
        List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();
        if(messages.isEmpty()){
        	System.out.println("didn't get any message");
        }else{
            for (Message m : messages) {
            	System.out.println(m.getMessageId());
            	System.out.println(m.getBody());
                sqs.deleteMessage(queueUrl, m.getReceiptHandle());
            }
        }

    }

}
