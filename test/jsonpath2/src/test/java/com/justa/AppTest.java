package com.justa;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.internal.JsonFormatter;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

/**
 * Unit test for simple App.
 */
public class AppTest   
{
	private static final Configuration configuration = Configuration.builder()
		    .jsonProvider(new JacksonJsonNodeJsonProvider())
		    .mappingProvider(new JacksonMappingProvider())
		    .build();
	
    private static final String originalJson = "{\n"
	        + "\"session\":\n"
	        + "    {\n"
	        + "        \"name\":\"JSESSIONID\",\n"
	        + "        \"value\":\"5864FD56A1F84D5B0233E641B5D63B52\"\n"
	        + "    },\n"
	        + "\"loginInfo\":\n"
	        + "    {\n"
	        + "        \"loginCount\":77,\n"
	        + "        \"previousLoginTime\":\"2014-12-02T11:11:58.561+0530\"\n"
	        + "    }\n"
	        + "}";
	
	//@Test
	public void a_value_can_be_updated(){



	    
	    System.out.println(originalJson);
	    JsonNode updatedJson = JsonPath.using(configuration).parse(originalJson)
	    		.set("$.session.name", "__newValueA")
	    		//.add("$.session2", "__newValueB")
	    		.json();	    

	    System.out.println(updatedJson.toPrettyString());
	}
	
	@Test
	public void a_node_can_be_updated_and_created() throws ParseException{
		
		System.out.println(originalJson);
		JSONObject json = (JSONObject)new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(originalJson);
		JSONObject session = JsonPath.read(json, "$.session");
		session.appendField("new__field", "new__value");
		session.put("name", "Justin2");
		System.out.println(JsonFormatter.prettyPrint(json.toJSONString()) );
		
	}
	
    
}
