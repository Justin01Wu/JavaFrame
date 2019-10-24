package com.justa.test.aws.message;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;


// comes from https://docs.aws.amazon.com/sns/latest/dg/sns-tutorial-publish-message-to-topic.html
public class PublishSNSMessage {
	
	private static final String topicArn = "arn:aws:sns:us-east-1:137200312110:MyTopic" ;
	
    public static void main(String[] args)    {
    	
    	final AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();
    	// Publish a message to an Amazon SNS topic.
    	final String msg = "If you receive this message, publishing a message to an Amazon SNS topic works.";
    	final PublishRequest publishRequest = new PublishRequest(topicArn, msg);
    	final PublishResult publishResponse = snsClient.publish(publishRequest);

    	// Print the MessageId of the message.
    	System.out.println("MessageId: " + publishResponse.getMessageId());
    }
}
