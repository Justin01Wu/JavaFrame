package com.justa.test.aws.dynamo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;

// it comes from https://docs.aws.amazon.com/en_pv/sdk-for-java/v1/developer-guide/examples-dynamodb-items.html
public class GetItemFromDynamoDb {
	
	private static final String tableName = "UserProfile";
	private static final String userKey = "D:W9001-79693-00345";
	private static final String keyFieldName = "userID";	 
	
	// try DynamoDB Streams
	
	public static void main(String[] args) throws IOException {
		
		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder
				.standard()
				.withRegion(Regions.US_EAST_1)
				.build();

		HashMap<String, AttributeValue> key_to_get = new HashMap<String, AttributeValue>();

		key_to_get.put(keyFieldName, new AttributeValue(userKey));

		GetItemRequest request = null;

		request = new GetItemRequest().withKey(key_to_get)
				.withTableName(tableName)
				.withConsistentRead(true);  // use strongly consistent reads
		// A strongly consistent read might not be available if there is a network delay or outage

		try {
			Map<String, AttributeValue> returned_item = ddb.getItem(request).getItem();
			if (returned_item != null) {
				Set<String> keys = returned_item.keySet();
				for (String key : keys) {
					System.out.format("%s: %s\n", key, returned_item.get(key).toString());
				}
			} else {
				System.out.format("No item found with the key %s!\n", userKey);
			}
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);

		}

	}
}
