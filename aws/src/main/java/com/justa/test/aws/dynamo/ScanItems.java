package com.justa.test.aws.dynamo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

public class ScanItems {

	private static final String tableName = "Music";

	public static void main(String[] args) throws IOException {
		final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

		Map<String, AttributeValue> expressionAttributeValues = new HashMap<String, AttributeValue>();
		expressionAttributeValues.put(":p1", new AttributeValue().withN("1"));
		expressionAttributeValues.put(":p2", new AttributeValue().withN("2"));

		ScanRequest scanRequest = new ScanRequest()
				.withTableName(tableName)
				.withFilterExpression("Price > :p1 and Price < :p2")
				.withExpressionAttributeValues(expressionAttributeValues);

		ScanResult result = client.scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			System.out.println("SongTitle:" + item.get("SongTitle") + ", Price:" + item.get("Price") + ", Genre: "
					+ item.get("Genre"));
		}
	}
}
