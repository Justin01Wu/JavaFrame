package com.justa.library.test.avro;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
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
	public void testPerson() throws IOException {
		Schema schema = getSchema("/avro/person.json");
		
		//String jsonString = "{\"firstName\": \"Justin\", \"lastName\": \"Wu\"}";  
		//  will fail without gender even it has default value
		
		String jsonString = "{\"firstName\": \"Justin\", \"lastName\": \"Wu\", \"gender\": \"Female\"}";
		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
		
		// failed : test alias familyName reading
//		jsonString = "{\"firstName\": \"Justin\", \"familyName\": \"Wu\", \"gender\": \"Female\"}";
//		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
		
	}
	
	@Test 
	public void testPersonSerialize() throws IOException, URISyntaxException {
		Schema schema = getSchema("/avro/person.json");
		GenericRecord person = new GenericData.Record(schema);
		person.put("firstName", "Justin");
		person.put("lastName", "Wu");
		person.put("gender", "Female");  // will fail without it even it has default value
		String result = AvroWithoutCodeGeneration.jsonSerialize(person, schema);
		System.out.println(result);

		// failed: test Alias on writing
//		person = new GenericData.Record(schema);
//		person.put("firstName", "Justin");
//		person.put("familyName", "Wu");  // this is alias field
//		person.put("gender", "Female");  // will fail without it even it has default value
//		result = AvroWithoutCodeGeneration.jsonSerialize(person, schema);
//		System.out.println(result);

		

	}

	@Test
	public void testLogicalType() throws IOException {
		Schema schema = getSchema("/avro/logical_type.json");
		
		String jsonString = "{\"postCodeCA\": \"L6M0V8\"}";

		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
	}
	
	@Test
	public void testLogicalTypeFailure() throws IOException {
		Schema schema = getSchema("/avro/logical_type.json");
		
		String jsonString = "{\"postCodeCA\": \"LKM0V8\"}";
		// should fail, TODO : fix it
		// comes from https://stackoverflow.com/questions/37279096/data-validation-in-avro

		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
	}

	@Test
	public void testObjectArray() throws IOException {
		Schema schema = getSchema("/avro/object_list.json");
		
		String jsonString = "{\"children\": [   {\"name\":\"Justin\", \"birthday\":\"adsjkhasd\",\"gender\":\"Female\"} ]}";
		// gender can't be skipped, even it has default value TODO what's wrong?
		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
	}
	
	@Test(expected =  AvroTypeException.class)
	//AvroTypeException: Expected field name not found: name
	public void testObjectArrayFailure() throws IOException {
		Schema schema = getSchema("/avro/object_list.json");
		
		String jsonString = "{\"children\": [   {\"name2\":\"Justin\", \"birthday\":\"adsjkhasd\"} ]}";

		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
	}

	
	@Test
	public void testintArraySchema() throws IOException {
		Schema schema = getSchema("/avro/int_array.json");
		GenericRecord experience = new GenericData.Record(schema);
		List<Integer> ages = new ArrayList<>();
		ages.add(1234);
		ages.add(2345);
		experience.put("ages", ages);
		
		String jsonString = "{\"ages\": [1234,3456]}";

		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
	}
	
	@Test(expected =  AvroTypeException.class)
	// AvroTypeException: Expected int. Got VALUE_STRING
	public void testintArrayFailure() throws IOException {
		Schema schema = getSchema("/avro/int_array.json");
		String jsonString = "{\"ages\": [\"1234\",\"3456\"]}";

		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
	}


	@Test
	public void testEnumSchema() throws IOException {
		Schema schema = getSchema("/avro/enum_sample.json");
		GenericRecord experience = new GenericData.Record(schema);
		experience.put("color", "RED");
		
		experience = new GenericData.Record(schema);
		experience.put("color", "InvalidColor");  // this succeed, why?
		
		String jsonString = "{\"color\": \"BLUE\"}";

		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
	}
	
	@Test(expected =  AvroTypeException.class)
	// AvroTypeException: Unknown symbol in enum InvalidColor
	public void testEnumSchemaFailure() throws IOException {
		Schema schema = getSchema("/avro/enum_sample.json");
	
		String jsonString = "{\"color\": \"InvalidColor\"}";

		AvroWithoutCodeGeneration.jsonDeserialize(schema, jsonString);
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
		
		String result = AvroWithoutCodeGeneration.jsonSerialize(users.get(0), schema);
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
