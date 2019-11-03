package com.justa.test.aws.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
	public void testHandleInput() throws URISyntaxException, IOException {
		String fileName = VerifyNewS3ObjectTest.class.getSimpleName() + ".json";
		
		String json = readJSONFile(fileName);
		
		VerifyNewS3Object.handleInput(json);
	}
	
	@Test
	public void testVerifyTextInputStream() throws URISyntaxException, IOException {
		String fileName = VerifyNewS3ObjectTest.class.getSimpleName() + ".prere.txt";
		File targetFile = new File("src\\test\\resources\\" + fileName);
		FileInputStream fin=new FileInputStream(targetFile);
		InputStreamReader reader =  new InputStreamReader(fin);
		String msg = VerifyNewS3Object.verifyTextInputStream(reader);
		if(msg != null){
			System.out.println(msg);
		}
	}

}
