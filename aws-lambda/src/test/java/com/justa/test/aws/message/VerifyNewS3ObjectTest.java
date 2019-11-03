package com.justa.test.aws.message;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.junit.Test;

public class VerifyNewS3ObjectTest {
	
	public static String readJSONFile(String fileName) throws FileNotFoundException, URISyntaxException {

		File targetFile = new File("src\\test\\resources\\" + fileName);
		System.out.println("Json File: " + targetFile.getAbsolutePath());

		Scanner scanner = new Scanner(targetFile);
		String content = scanner.useDelimiter("\\Z").next();
		scanner.close();

		return content;
	}

	@Test
	public void test() throws URISyntaxException, IOException {
		String sample = VerifyNewS3ObjectTest.class.getSimpleName() + ".json";
		
		String json = readJSONFile(sample);
		
		VerifyNewS3Object.handleInput(json);
	}

}
