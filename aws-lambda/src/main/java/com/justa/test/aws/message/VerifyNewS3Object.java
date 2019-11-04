package com.justa.test.aws.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VerifyNewS3Object implements RequestHandler<Map<String, Object>,String> {
	
    private static final AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
    private final static AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();
    // must set region = us-east-1 in C:\Users\USERNAME\.aws\config  [default] section
    
    private static String topicArn = System.getenv("SNS_TOPIC_ARN");  //String topicArn = "arn:aws:sns:us-east-1:137200312110:MyTopic";
    private static String debug = System.getenv("debug");
	
	@Override
	public String handleRequest(Map<String, Object> input, Context context) {		
			                    
		System.out.println("    input type: " + input.getClass().getName());
		System.out.println("    input value: " + input);

		try {
			ObjectMapper Obj = new ObjectMapper(); 
			  
	        String jsonStr = Obj.writeValueAsString(input); 
	        System.out.println("    jsonStr: " + jsonStr);
			handleInput(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static void handleInput(String jsonStr) throws IOException{
		
		S3EventNotification s3Event = S3EventNotification.parseJson(jsonStr);
		S3EventNotificationRecord record = s3Event.getRecords().get(0);
		String bucketName = record.getS3().getBucket().getName();
		String objectName = record.getS3().getObject().getUrlDecodedKey();
		
		// the following way is more generic 
//		Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonStr);		
//		String bucketName = JsonPath.read(document, "$.Records[0].s3.bucket.name");
//		String objectName = JsonPath.read(document, "$.Records[0].s3.object.key");
		
		System.out.println("    bucketName: " + bucketName);
		System.out.println("    objectName: " + objectName);
		if(!objectName.endsWith(".prere.txt")){
			String msg = "    file name must end with .prere.txt";
			writeMsg(msg);
		}
		
		S3Object fullObject = s3Client.getObject(new GetObjectRequest(bucketName, objectName));
		
        InputStreamReader isr3 = new InputStreamReader(fullObject.getObjectContent());
        String msg = verifyTextInputStream(isr3);
        if(msg != null){
        	writeMsg(msg);
        }
		
		
	}
	
    static String verifyTextInputStream(InputStreamReader isr) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(isr);
        String line = null;
        int rows = 0;
        int size = 0;
        String msg;
        while ((line = reader.readLine()) != null) {

        	size = size + line.length();  
            rows++;
            
           	if(rows%1000 == 1){
           		System.out.println("    rows = " + rows +", size = " + size);
           		System.out.println("    " + line);           		
           	}
           	
            String[] fields = line.split("\t");
            if(fields.length != 5){
            	msg = "    expected 5 fields, but not at row " + rows;
            	msg = msg + "\r\n      : " + line;
            	return msg;
            }
           	
            if(rows>1){
            	// IterId	EVENTID	SeqId	LOSS	riskGroup
            	//27	600000002	2013-09-06 21:22:00	13591155.1216389	EuropeEQ
            	if(!verifyInteger(fields[0])){
            		msg = "    expected integer on field 1 at row " + rows+":" ;
            		msg = msg + "\r\n      : " + line;
            		return msg;
            	}
            	if(!verifyInteger(fields[1])){
            		msg = "    expected integer on field 2 at row " + rows+":" ;
            		msg = msg + "\r\n      : " + line;
            		return msg;
            	}
            	if(!verifyDouble(fields[3])){
            		msg = "    expected integer on field 4 at row " + rows+":" ;
            		msg = msg + "\r\n      : " + line;
            		return msg;
            	}
            }           	

        }
        return null;
        
    }
    
    private static void writeMsg(String msg){
		if(topicArn == null){
			System.out.println("    Didn't find Environment variable SNS_TOPIC_ARN, so do nothing");
			System.out.println(msg);
			return ;
		}else{
			PublishRequest publishRequest = new PublishRequest(topicArn, msg);
			PublishResult publishResponse = snsClient.publish(publishRequest);
			System.out.println("    MessageId: " + publishResponse.getMessageId());
		}
    }
    
    private static boolean verifyInteger(String value){
    	try{
    		Integer.parseInt(value);
    		return true;
    	}catch(NumberFormatException e){
    		return false;
    	}
    }
    
    private static boolean verifyDouble(String value){
    	try{
    		Double.parseDouble(value);
    		return true;
    	}catch(NumberFormatException e){
    		return false;
    	}
    }
}
