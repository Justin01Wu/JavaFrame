package com.justa.library.test.avro;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.avro.AvroTypeException;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.junit.Test;

import com.justa.library.FileUtil;

public class AvroWithoutCodeGenerationTest {
	
	private static Schema getSchema() throws IOException {
		Schema schema = getSchema("/avro/user.avsc");
		return schema;
	}
	
	private static Schema getSchema(String schemaUrl) throws IOException {
		InputStream define = AvroWithoutCodeGenerationTest.class.getResourceAsStream(schemaUrl);
		
		Schema schema = AvroWithoutCodeGeneration.getSchema(define);
		return schema;
	}
	
	

	@Test
	public void testIntSchema() throws IOException {
		Schema schema = getSchema("/avro/int_sample.avsc");
		GenericRecord experience = new GenericData.Record(schema);
		experience.put("experience", 1223);
		
		experience = new GenericData.Record(schema);
		experience.put("experience", "sss");  // this succeed, why?
		
		String jsonString = "{\"experience\":12345}";

		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
	}
	
	@Test(expected =  AvroTypeException.class)
	// AvroTypeException: Expected start-union. Got VALUE_STRING
	public void testIntSchemaFailure() throws IOException {
		Schema schema = getSchema("/avro/int_sample.avsc");
		
		String jsonString = "{\"experience\":\"sss\"}";

		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
	}


	
	@Test
	public void testUnionSchema() throws IOException {
		Schema schema = getSchema("/avro/union_sample.avsc");
		GenericRecord experience = new GenericData.Record(schema);
		experience.put("experience", 1223);
		
		experience = new GenericData.Record(schema);
		experience.put("experience", null);  
		
		String jsonString = "{\"experience\":{\"int\":445}}";
		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
		
		//jsonString = "{\"experience\":null}";
		//AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);

	}
	
	@Test(expected =  AvroTypeException.class)
	// AvroTypeException: Expected start-union. Got VALUE_STRING
	public void testUnionSchemaFailure() throws IOException {
		Schema schema = getSchema("/avro/union_sample.avsc");
		GenericRecord experience = new GenericData.Record(schema);
		
		experience.put("experience", "asas"); // this succeed, why?

		String jsonString = "{\"experience\":2345}";
		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);

		//jsonString = "{\"experience\":\"sss\"}";
		//AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
		
	}


	@Test
	public void testGetSchema() throws IOException {
		Schema schema = getSchema();
		
		System.out.println(schema.getFullName());
		System.out.println(schema.getName());
		System.out.println(schema.getType());
		System.out.println(schema.getField("name").toString());
		System.out.println(schema.getField("favorite_number").toString());
		
		assertEquals("example.avro.User", schema.getFullName());
		assertEquals("User", schema.getName());
		assertEquals("RECORD", schema.getType().toString());
		
		
		
	}
	
	//@Test
	public void testCreateUserObjAndSerierize() throws IOException, URISyntaxException {
		Schema schema = getSchema();
		List<GenericRecord> users= AvroWithoutCodeGeneration.createUsers(schema);
		
		assertEquals(users.size(), 2);
		
		// Serialize user1 and user2 to disk

		File file = new File("c:/temp/users.avro");
		AvroWithoutCodeGeneration.serialize(file, users, schema);
		
		

	}
	
	@Test 
	public void testCreateUserObjAndJsonSerierize() throws IOException, URISyntaxException {
		Schema schema = getSchema();
		List<GenericRecord> users= AvroWithoutCodeGeneration.createUsers(schema);
		
		assertEquals(users.size(), 2);
		
		String result = AvroWithoutCodeGeneration.jsonSerialize(users, schema);
		System.out.println(result);

	}

	@Test 
	public void testDeserialize() throws IOException, URISyntaxException {
		
		Schema schema = getSchema();
		
		URL url = AvroWithoutCodeGenerationTest.class.getResource("/avro/users.avro");
		System.out.println(url.getFile());
		File file = new File(url.getFile());
		
		AvroWithoutCodeGeneration.deserialize(schema, file);
	}
	

	@Test 
	public void testJsonDeserialize() throws IOException, URISyntaxException {
		
		Schema schema = getSchema();
		String jsonString = FileUtil.readFileFromClassRoot("/avro/oneUser.json");

		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
	}

	@Test(expected =  AvroTypeException.class)
	// AvroTypeException: Expected field name not found: name
	public void testJsonWrongName() throws IOException, URISyntaxException {
		
		Schema schema = getSchema();
		String jsonString = FileUtil.readFileFromClassRoot("/avro/wrongName.json");
		
		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
	}	

	@Test (expected =  AvroTypeException.class) 
	// AvroTypeException :  Expected field name not found: favorite_number
	public void testJsonWrongNumber() throws IOException, URISyntaxException {
		
		Schema schema = getSchema();
		String jsonString = FileUtil.readFileFromClassRoot("/avro/wrongNumber.json");
		
		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
	}	
	
	@Test 
	public void testJsonExtraField() throws IOException, URISyntaxException {
		
		Schema schema = getSchema();
		
		String jsonString = FileUtil.readFileFromClassRoot("/avro/extraField.json");

		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
	}	
	
	

}
