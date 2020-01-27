package com.justa.test.aws.dynamo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

// https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GettingStarted.Java.03.html#GettingStarted.Java.03.05
// first writing wins, others failed  (Optimistic locking?)
// TODO test DynamoDBVersionAttribute
public class ConcurrentConditionalWrite {
	private static final String tableName = "Music";
	private static final PrimaryKey key = new PrimaryKey("Artist", "The Acme Band", "SongTitle", "Still In Love");
	// partition key plus sort key
	
	public static void main(String[] args) throws IOException {
		final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable("Music");
		List<ItemWriter> writers = new ArrayList<>();
		
		for(int i=0; i<=5; i++) {
			ItemWriter writer = getWriter(dynamoDB, table);
			writers.add(writer);
		}
		for(int i=0; i<writers.size(); i++) {
			writers.get(i).start();	
		}

	}
	
	private static ItemWriter getWriter(DynamoDB dynamoDB, Table table) {
		Item item = getItemByPk(dynamoDB);
		ItemWriter t1 = new ItemWriter(item, table);
		return t1;		
	}
	
	private static Item getItemByPk(DynamoDB dynamoDB) {
		Table table = dynamoDB.getTable(tableName);
		
		GetItemSpec request = null;	

		request = new GetItemSpec().withPrimaryKey(key)
				.withConsistentRead(true); 
			
		// use strongly consistent reads
		// A strongly consistent read might not be available if there is a network delay  or outrage
		
        Item outcome = table.getItem(request);
        System.out.println("GetItem succeeded: " + outcome);	
        return outcome;
	}
	
	static class ItemWriter extends Thread {
		private Item item;
		private Table table;
		
		public ItemWriter(Item item, Table table){
			this.item = item;
			this.table = table;
		}	    

	    public void run() {

	    	long threadId = Thread.currentThread().getId();
			Number price = item.getNumber("Price");
			
			ValueMap valueMap = new ValueMap();

			valueMap.withNumber(":newPrice", threadId);
			valueMap.withNumber(":oldPrice", price);
			
			UpdateItemSpec updateItemSpec = new UpdateItemSpec()
		            .withPrimaryKey(key)
		            .withUpdateExpression("set Price =  :newPrice")
		            .withConditionExpression("Price = :oldPrice")
		            .withValueMap(valueMap)
		            .withReturnValues(ReturnValue.UPDATED_NEW);
			
			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);			
			
			 System.out.println("UpdateItem succeededin thread threadId: " + threadId + "," + outcome.getItem().toJSONPretty());
	    }
	}
}
