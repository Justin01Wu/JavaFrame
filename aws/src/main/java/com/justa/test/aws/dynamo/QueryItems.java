package com.justa.test.aws.dynamo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

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

public class QueryItems {
	private static final String tableName = "Music";
	
	public static void main(String[] args) throws IOException {
		final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable(tableName);
		
        // use primary partition key only
        ItemCollection<QueryOutcome> items = table.query("Artist", "The Acme Band");  
        Iterator<Item> iterator  = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            System.out.println("SongTitle:" + item.getString("SongTitle") + ", Price:" + item.getNumber("Price") + ", Genre: " + item.getString("Genre"));
        }
        
        System.out.println("");
        queryPrimaryKey(table);
        
        System.out.println("");
        querySortRange(table);
        
        System.out.println("");
        queryIndexRange(table);


	}
	
	private static void queryPrimaryKey(Table table) {
	     // use primary partition key range 
			QuerySpec spec = new QuerySpec()
				    .withKeyConditionExpression("Artist = :atst")
				    .withValueMap(new ValueMap()
				        .withString(":atst", "The Acme Band")
				        );
			ItemCollection<QueryOutcome> items = table.query(spec);  
			Iterator<Item> iterator  = items.iterator();
	        while (iterator.hasNext()) {
	            Item item = iterator.next();
	            System.out.println("SongTitle:" + item.getString("SongTitle") + ", Price:" + item.getNumber("Price") + ", Genre: " + item.getString("Genre"));
	        }
	}
	
	private static void querySortRange(Table table) {
        // use primary partition key(Artist) plus sort key(SongTitle ) range        

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":myArtist", "The Acme Band");
        valueMap.put(":letter1", "L");
        valueMap.put(":letter2", "S");  // this will exclude song "Still In Love" because "Still" is after "S"
		QuerySpec spec = new QuerySpec()
			    .withKeyConditionExpression("Artist = :myArtist and SongTitle between :letter1 and :letter2")
			    .withValueMap(valueMap);
		ItemCollection<QueryOutcome> items = table.query(spec);  
		Iterator<Item> iterator  = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            System.out.println("SongTitle:" + item.getString("SongTitle") + ", Price:" + item.getNumber("Price") + ", Genre: " + item.getString("Genre"));
        }
	}
	
	private static void queryIndexRange(Table table) {
        // use Index partition key(Genre) range

		Index index = table.getIndex("Genre-AlbumTitle-index");	
		
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":gene", "Country");
        valueMap.put(":letter1", "H");
        valueMap.put(":letter2", "Sp");  // In this way, it includes AlbumTitle "Somewhat Famous" 
		QuerySpec spec = new QuerySpec()
			    .withKeyConditionExpression("Genre  = :gene and AlbumTitle between :letter1 and :letter2 ")
			    .withValueMap(valueMap);
		ItemCollection<QueryOutcome> items = index.query(spec);  
		Iterator<Item> iterator  = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            System.out.println("SongTitle:" + item.getString("SongTitle") + ", Price:" + item.getNumber("Price") + ", Genre: " + item.getString("Genre"));
        }
	}
}
