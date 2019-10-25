package com.justa.test.aws.dynamo;

import java.io.IOException;
import java.util.Iterator;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

public class QueryItemFromDynamoDb {
	private static final String tableName = "UserProfile";

	public static void main(String[] args) throws IOException {
		
		final AmazonDynamoDB client  = AmazonDynamoDBClientBuilder
				.standard()
				.withRegion(Regions.US_EAST_1)
				.build();
		
		DynamoDB dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable(tableName);
		Index index = table.getIndex("employeeId-index");		

		QuerySpec spec = new QuerySpec()
		    .withKeyConditionExpression("employeeId = :eId")
		    .withValueMap(new ValueMap()
		        .withNumber(":eId",223467)
		        );

		ItemCollection<QueryOutcome> items = index.query(spec);
		Iterator<Item> iter = items.iterator(); 
		while (iter.hasNext()) {
		    System.out.println(iter.next().toJSONPretty());
		}
		
		System.out.println("Done.");

	}
}
