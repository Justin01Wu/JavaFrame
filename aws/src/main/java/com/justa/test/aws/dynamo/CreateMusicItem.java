package com.justa.test.aws.dynamo;

import java.util.Arrays;
import java.util.HashSet;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

// for AWS Certified Developer Official Study Guide.pdf page 203 
public class CreateMusicItem {
	public static void main(String[] args) {

		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

		DynamoDB dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable("Music");

		System.out.println("Adding new items...");
		
		Item item = getItemA();
		PutItemOutcome outcome = table.putItem(item);
		System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

		item = getItemB();
		outcome = table.putItem(item);
		System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
		
		item = getItemC();
		outcome = table.putItem(item);
		System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
		
		item = getItemD();
		outcome = table.putItem(item);
		System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

	}

	private static Item getItemA() {
		PrimaryKey key = new PrimaryKey("Artist", "No One You Know", "SongTitle", "My Dog Spot");
		// partition key plus sort key
		Item item = new Item().withPrimaryKey(key)
				.withString("SongTitle", "My Dog Spot")
				.withString("AlbumTitle", "Hey Now")
				.withString("Genre", "Country")	
				.withNumber("CriticRating", 8.4)
				.withNumber("Price", 1.98);
		return item;
	}

	private static Item getItemB() {
		PrimaryKey key = new PrimaryKey("Artist", "Somewhere Down The Road", "SongTitle", "My Dog Spot");
		Item item = new Item().withPrimaryKey(key)
				.withString("AlbumTitle", "Somewhat Famous")
				.withString("Genre", "Country")
				.withStringSet("Authors", new HashSet<String>(Arrays.asList("Author12", "Author22")))
				.withNumber("CriticRating", 8.4)
				.withNumber("Year", 1984);
		return item;
	}
	
	private static Item getItemC() {
		PrimaryKey key = new PrimaryKey("Artist", "The Acme Band", "SongTitle", "Still In Love");
		Item item = new Item().withPrimaryKey(key)
				.withString("AlbumTitle", "The Buck Starts Here")
				.withString("Genre", "Rock")
				.withNumber("Price", 2.47);
		return item;
	}
	
	private static Item getItemD() {
		PrimaryKey key = new PrimaryKey("Artist", "The Acme Band", "SongTitle", "Look Out, World");
		Item item = new Item().withPrimaryKey(key)
				.withString("AlbumTitle", "The Buck Starts Here")
				.withString("Genre", "Rock")
				.withNumber("Price", 0.99);
		return item;
	}
}
