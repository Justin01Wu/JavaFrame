package com.justa.test.aws.dynamo;

import java.io.IOException;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

public class GetItemByPK {

	private static final String tableName = "Music";
	private static final PrimaryKey key = new PrimaryKey("Artist", "Somewhere Down The Road", "SongTitle", "My Dog Spot");
	// partition key plus sort key
	
	public static void main(String[] args) throws IOException {
		final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable(tableName);
	
		GetItemSpec request = null;	

		request = new GetItemSpec().withPrimaryKey(key)
				.withConsistentRead(true); 
			
		// use strongly consistent reads
		// A strongly consistent read might not be available if there is a network delay  or outrage
		
        Item outcome = table.getItem(request);
        System.out.println("GetItem succeeded: " + outcome);


	}
}
