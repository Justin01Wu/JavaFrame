package com.justa.test.aws.s3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CSVInput;
import com.amazonaws.services.s3.model.CSVOutput;
import com.amazonaws.services.s3.model.CompressionType;
import com.amazonaws.services.s3.model.ExpressionType;
import com.amazonaws.services.s3.model.FileHeaderInfo;
import com.amazonaws.services.s3.model.InputSerialization;
import com.amazonaws.services.s3.model.JSONInput;
import com.amazonaws.services.s3.model.JSONType;
import com.amazonaws.services.s3.model.OutputSerialization;
import com.amazonaws.services.s3.model.SelectObjectContentEvent;
import com.amazonaws.services.s3.model.SelectObjectContentEventVisitor;
import com.amazonaws.services.s3.model.SelectObjectContentRequest;
import com.amazonaws.services.s3.model.SelectObjectContentResult;

public class QueryS3Content {
	private static Regions clientRegion = Regions.US_EAST_1;
	private static String bucketName = "developer719";

	public static void main(String[] args) throws IOException {
		
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();

		queryJson(s3Client);
		queryCSV(s3Client);

	}
	
	private static void queryCSV(AmazonS3 s3Client) throws IOException {
		/**
		 * sample data: 
			firstName;lastName
			justin;wu
		 */
		String query = "select s.firstName, s.lastName from s3object s where s.firstName ='justin'";
		SelectObjectContentRequest selectRequest = generateBaseCSVRequest(bucketName, "users.csv", query);

		SelectObjectContentResult result = s3Client.selectObjectContent(selectRequest);
		
		printResult(result);
	}
	
	private static void queryJson(AmazonS3 s3Client) throws IOException {
		/**
		 * sample data: 
		 * {"name": "Joe", "company": "AMAZON", "favorite_color": "blue"}
		 * {"name": "Mike", "company": "WHOLE FOODS", "favorite_color": "green"}
		 */
		String query = "select s.name, s.company from s3object s where s.company ='AMAZON'";
		SelectObjectContentRequest selectRequest = generateBaseJsonRequest(bucketName, "jsonList.json", query);

		SelectObjectContentResult result = s3Client.selectObjectContent(selectRequest);
		
		printResult(result);
	}
	
	
	private static void printResult(SelectObjectContentResult result) throws IOException {
		final AtomicBoolean isResultComplete = new AtomicBoolean(false);
		InputStream resultInputStream = result.getPayload()
				.getRecordsInputStream(new SelectObjectContentEventVisitor() {
					@Override
					public void visit(SelectObjectContentEvent.StatsEvent event) {
						System.out.println("Received Stats, Bytes Scanned: " + event.getDetails().getBytesScanned()
								+ " Bytes Processed: " + event.getDetails().getBytesProcessed());
					}

					/*
					 * An End Event informs that the request has finished successfully.
					 */
					@Override
					public void visit(SelectObjectContentEvent.EndEvent event) {
						isResultComplete.set(true);
						System.out.println("Received End Event. Result is complete.");
					}
				});

		String str = convertInputStreamToString(resultInputStream);
		System.out.println(str);
	}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException {

		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}

		return result.toString(StandardCharsets.UTF_8.name());

	}

	private static SelectObjectContentRequest generateBaseCSVRequest(String bucket, String key, String query) {
		SelectObjectContentRequest request = new SelectObjectContentRequest();
		request.setBucketName(bucket);
		request.setKey(key);
		request.setExpression(query);
		request.setExpressionType(ExpressionType.SQL);

		InputSerialization inputSerialization = new InputSerialization();
		inputSerialization.setCsv(new CSVInput()
				.withFieldDelimiter(";")    // delimiter is ";" , default is ","
				.withFileHeaderInfo(FileHeaderInfo.USE)  // use first line as header
				);
		inputSerialization.setCompressionType(CompressionType.NONE);
		request.setInputSerialization(inputSerialization);

		OutputSerialization outputSerialization = new OutputSerialization();
		outputSerialization.setCsv(new CSVOutput());
		request.setOutputSerialization(outputSerialization);

		return request;
	}

	private static SelectObjectContentRequest generateBaseJsonRequest(String bucket, String key, String query) {
		SelectObjectContentRequest request = new SelectObjectContentRequest();
		request.setBucketName(bucket);
		request.setKey(key);
		request.setExpression(query);
		request.setExpressionType(ExpressionType.SQL);

		InputSerialization inputSerialization = new InputSerialization();
		inputSerialization.setJson(new JSONInput()
				.withType(JSONType.LINES)  // every line is a json object
				);
		inputSerialization.setCompressionType(CompressionType.NONE);
		request.setInputSerialization(inputSerialization);

		OutputSerialization outputSerialization = new OutputSerialization();
		outputSerialization.setCsv(new CSVOutput());
		request.setOutputSerialization(outputSerialization);

		return request;
	}
}
