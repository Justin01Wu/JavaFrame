package com.justa.test.aws.message;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

// comes from https://docs.aws.amazon.com/sns/latest/dg/sns-tutorial-publish-message-to-topic.html
// and https://docs.aws.amazon.com/lambda/latest/dg/java-programming-model-handler-types.html
public class PublishS3ChangeMsg implements RequestHandler<Map<String, Object>, String> {

	//private static final String topicArn = "arn:aws:sns:us-east-1:137200312110:MyTopic";

	@Override
	public String handleRequest(Map<String, Object> input, Context context) {		
		
		for(String key: input.keySet()){
			Object value = input.get(key);
			System.out.println("value type: " + value.getClass().getName());
		}
		System.out.println("input: " + input);
		
		String topicArn = System.getenv("SNS_TOPIC_ARN");
		if(topicArn == null){
			System.out.println("Didn't find Environment variable SNS_TOPIC_ARN, so do nothing");
			return null;
		}
		
		final AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();
		// Publish a message to an Amazon SNS topic.
		final String msg = "An S3 object is just changed: " + input;
		final PublishRequest publishRequest = new PublishRequest(topicArn, msg);
		final PublishResult publishResponse = snsClient.publish(publishRequest);

		// Print the MessageId of the message.
		System.out.println("MessageId: " + publishResponse.getMessageId());
		return publishResponse.getMessageId();
	}
}
