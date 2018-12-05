package com.justa.library.test.avro;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.junit.Test;

public class AvroWithoutCodeGenerationTest {
	
	private static Schema getSchema() throws IOException {
		InputStream define = AvroWithoutCodeGenerationTest.class.getResourceAsStream("/user.avsc");
		
		Schema schema = AvroWithoutCodeGeneration.getSchema(define);
		return schema;
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
		AvroWithoutCodeGeneration.serierize(file, users, schema);
		
		

	}
	
	@Test 
	public void testCreateUserObjAndJsonSerierize() throws IOException, URISyntaxException {
		Schema schema = getSchema();
		List<GenericRecord> users= AvroWithoutCodeGeneration.createUsers(schema);
		
		assertEquals(users.size(), 2);
		
		String result = AvroWithoutCodeGeneration.jsonSerierize(users, schema);
		System.out.println(result);
		
		

	}

	
	

}
