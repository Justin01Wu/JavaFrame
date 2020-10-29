package com.justa.test.aws.message;

import java.util.Date;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

// it comes from https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-sqs-messages.html
public class SendSQSMessages {

	private static final String QUEUE_NAME = "input" ;
	
    public static void main(String[] args)    {

    	
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();//.standard().withRegion("us-east-1").defaultClient();
        
        String queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
     // receive messages from the queue
        
        String msgBody = "Justin test from sendSQSMessages at " + new Date();
        
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
        		.withQueueUrl(queueUrl)                
                .withMessageBody(msgBody);
        
        System.out.println("start to send message..." + new Date());
        
        sqs.sendMessage(sendMessageRequest);
        
        System.out.println("end at send message..." + new Date());

    }

}
