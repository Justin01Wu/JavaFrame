package com.justa.library.test.jackson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// it comes from https://www.mkyong.com/java/how-to-convert-java-object-to-from-json-jackson/
public class TestJackson {
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {	
		
		testSimpleClientFromString();
		System.out.println();
		testmanyClientFromFile();

	}
	
	private static void testSimpleClientFromString() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		// Convert JSON string from file to Object
		//User user = mapper.readValue(new File("G:\\user.json"), User.class);
		//System.out.println(user);

		// Convert JSON string to Object
		String jsonInString = "{\"id\":33,\"name\":\"AIG\",\"dnfReinsurer\":\"justin\"}";
		Client client1 = mapper.readValue(jsonInString, Client.class);
		System.out.println(client1);

	}
	
	private static void testmanyClientFromFile() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		// Convert JSON string from file to Object
		InputStream jsonFile = TestJackson.class.getResourceAsStream("/clients.json");
				
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Client.class);
		List <Client> result = mapper.readValue(jsonFile, type);
		for(Client client:  result) {
			System.out.println(client);	
		}
		


	}

}
