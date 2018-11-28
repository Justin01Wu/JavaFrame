package com.justa.library.test.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJackson {
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();

		// Convert JSON string from file to Object
		//User user = mapper.readValue(new File("G:\\user.json"), User.class);
		//System.out.println(user);

		// Convert JSON string to Object
		String jsonInString = "{\"id\":33,\"name\":\"AIG\",\"dnfReinsurer\":\"justin\"}";
		Client user1 = mapper.readValue(jsonInString, Client.class);
		System.out.println(user1);


	}
}
