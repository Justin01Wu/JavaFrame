package com.justa.test.aws.dynamo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

// by default, last writing wins 
public class ConcurrentWrite {
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
			price =   threadId;
			item.withNumber("Price", price);
			
			PutItemOutcome outcome = table.putItem(item);
			System.out.println("PutItem succeeded in thread threadId: " + threadId + ", "+ outcome.getPutItemResult());
	    }
	}
}
