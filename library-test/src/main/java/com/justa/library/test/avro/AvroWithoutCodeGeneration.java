package com.justa.library.test.avro;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;

// comes from : https://avro.apache.org/docs/1.8.2/gettingstartedjava.html
public class AvroWithoutCodeGeneration {

	public static Schema getSchema(InputStream define) throws IOException {

		Schema schema = new Schema.Parser().parse(define);
		return schema;
	}

	public static List<GenericRecord> createUsers(Schema schema) throws IOException {

		List<GenericRecord> users = new ArrayList<>();

		// Using this schema, let's create some users.

		GenericRecord user1 = new GenericData.Record(schema);
		user1.put("name", "Justin");
		user1.put("favorite_number", 256);
		// Leave favorite color null because it is optional

		// user1.put("invalidField", 256); // this line will throw exception on runtime

		users.add(user1);

		GenericRecord user2 = new GenericData.Record(schema);
		user2.put("name", "Rita");
		user2.put("favorite_number", 7);
		user2.put("favorite_color", "red");
		users.add(user2);

		return users;
	}

	public static String jsonSerialize(List<GenericRecord> users, Schema schema) throws IOException {
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		Encoder jsonEncoder = EncoderFactory.get().jsonEncoder(schema, bout);  // use JSON format for output data 
		
		DatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(schema);
		GenericRecord user  =  users.get(0); 
		writer.write(user, jsonEncoder);
		
		jsonEncoder.flush();
		String data = bout.toString();
		return data;
		
	}

	public static void serialize(File file, List<GenericRecord> users, Schema schema) throws IOException {
		DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
		DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
		dataFileWriter.create(schema, file);

		for (GenericRecord user : users) {
			dataFileWriter.append(user);  // by default, it is binary format
		}
		dataFileWriter.close();
	}

	public static void deserialize(Schema schema, File file) throws IOException {
		// deserialize users from disk
		DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
		DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file, datumReader);
		GenericRecord user = null;
		while (dataFileReader.hasNext()) {
			// Reuse user object by passing it to next(). This saves us from
			// allocating and garbage collecting many objects for files with
			// many items.
			user = dataFileReader.next(user);
			System.out.println(user);
		}
		dataFileReader.close();
	}
}
