package com.justa.library.test.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

import javax.json.Json;
import javax.json.JsonPatch;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

// comes from https://stackoverflow.com/questions/50967015/how-to-compare-json-documents-and-return-the-differences-with-jackson-or-gson
// this sample tell what is difference between two json objects
public class JsonComparing {
	
	
	public static String format(JsonValue json) {
	    StringWriter stringWriter = new StringWriter();
	    prettyPrint(json, stringWriter);
	    return stringWriter.toString();
	}

	public static void prettyPrint(JsonValue json, Writer writer) {
	    Map<String, Object> config =
	            Collections.singletonMap(JsonGenerator.PRETTY_PRINTING, true);
	    JsonWriterFactory writerFactory = Json.createWriterFactory(config);
	    try (JsonWriter jsonWriter = writerFactory.createWriter(writer)) {
	        jsonWriter.write(json);
	    }
	}
	
	public static String readJSONFile(String fileName) throws FileNotFoundException, URISyntaxException {

		File targetFile = new File("src\\main\\resources\\" + fileName);
		System.out.println("Json File: " + targetFile.getAbsolutePath());

		Scanner scanner = new Scanner(targetFile);
		String content = scanner.useDelimiter("\\Z").next();
		scanner.close();

		return content;
	}

	
	public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
		String fileA = JsonComparing.class.getSimpleName()+"_a.json";
		String fileB = JsonComparing.class.getSimpleName()+"_b.json";
		String leftJson = readJSONFile(fileA);
		String rightJson = readJSONFile(fileB);
		JsonValue source = Json.createReader(new StringReader(leftJson)).readValue();
		JsonValue target = Json.createReader(new StringReader(rightJson)).readValue();

		JsonPatch diff = Json.createDiff(source.asJsonObject(), target.asJsonObject());
		System.out.println(format(diff.toJsonArray()));
	}
}
