package com.justa.test.aws.dynamo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

// from https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBMapper.Annotations.html
public class DynamoDBMapperTest {
	
	public static void main(String[] args) throws IOException {
		
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        MusicItem obj = createMusticItem();
        mapper.save(obj);
        
        System.out.println("Object was saved with id " + obj.getArtist());
	}
	
	private static MusicItem createMusticItem() {
		MusicItem obj = new MusicItem();
        obj.setArtist("DynamoDBMapperTest:" + new Date().toString());  // hash key
        obj.setSongTitle("DynamoDBMapperTest: SongTitle");
        obj.setAlbumTitle("Hey Now");
        obj.setGenre("Rocky");
        obj.setYear(1990);
        obj.setCriticRating(1d);
        obj.setPrice(23.232d);
        
        List<String> authors =  new ArrayList<>();
        authors.add("Justin");
        authors.add("Rita");
        obj.setAuthors(authors);
        return obj;
	}
}
