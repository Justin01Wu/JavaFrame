package com.justa.library.test.avro;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.avro.Schema;
import org.junit.Test;

import com.justa.library.test.jackson.TestJackson;

public class AvroWithoutCodeGenerationTest {

	@Test
	public void testGetSchema() throws IOException {
		InputStream define = TestJackson.class.getResourceAsStream("/user.avsc");
		
		Schema schema = AvroWithoutCodeGeneration.getSchema(define);
		
		System.out.println(schema.getFullName());
		System.out.println(schema.getName());
		System.out.println(schema.getType());
		System.out.println(schema.getField("name").toString());
		System.out.println(schema.getField("favorite_number").toString());
		
		assertEquals("example.avro.User", schema.getFullName());
		assertEquals("User", schema.getName());
		assertEquals("RECORD", schema.getType().toString());
		
		
		
	}

}
