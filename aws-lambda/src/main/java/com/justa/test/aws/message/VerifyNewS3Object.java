package com.justa.test.aws.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

public class VerifyNewS3Object implements RequestHandler<Map<String, Object>,String> {
	
    private static final AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    private final static AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();
    
    private static String topicArn = System.getenv("SNS_TOPIC_ARN");  //String topicArn = "arn:aws:sns:us-east-1:137200312110:MyTopic";
	
	@Override
	public String handleRequest(Map<String, Object> input, Context context) {		
		/* event format:
		{
			  Records=[
			    {
			      messageId=cfcaa69e-feae-4f1d-a160-682e8aa706f3,
			      receiptHandle=AQEBJBPCvrEy6Gh/WzWUSWu+xygYAst2+WN2lzxrmcSk7ZvdrHdSsdlH9Rr2S4rdoVzH5uxYa2BELZF0q5Xf71/XIldcTXeib3sFQ30Oljf9GIDy326XM79ViXV/BcvK/tbatr0gr+xgTY8z+7zLr7Rn53VxqMfITktFz9ZHHGViyTlkP0/pNHERrIMWIA37YxInEnh5am5uASjUfGXroGCfwNypvaq4+gykhX44FPLOaGQ8e6mHapGWbDvWm4qjE57Uip0Fl0zZzz/OTyDknOaN7iXS3hTod0ntsW+bX7uDlldEIHsFeD5jXlJ7loh69BrXQKOGm0dE+XujcxniUPcB+bV9wBJEfdTNRoaB60Bol37naHYRdECS3KzgI6bAeKXo,
			      body={
			        "Records": [
			            */        
			                    
		System.out.println("input type: " + input.getClass().getName());
		System.out.println("input value: " + input);
		List<?> list = (List<?>)input.get("Records");
		Map<String, Object> one = (Map<String, Object>)(list.get(0));
		String json = one.get("body").toString();
		try {
			handleInput(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static void handleInput(String json) throws IOException{
		System.out.println("input value: " + json);
		
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
		
		String bucketName = JsonPath.read(document, "$.Records[0].s3.bucket.name");
		String objectName = JsonPath.read(document, "$.Records[0].s3.object.key");
		
		System.out.println("bucketName: " + bucketName);
		System.out.println("objectName: " + objectName);
		if(!objectName.endsWith(".prere.txt")){
			String msg = "file name must end with .prere.txt";
			if(topicArn == null){
				System.out.println("Didn't find Environment variable SNS_TOPIC_ARN, so do nothing");
				System.out.println(msg);
				return ;
			}else{
				PublishRequest publishRequest = new PublishRequest(topicArn, msg);
				PublishResult publishResponse = snsClient.publish(publishRequest);
				System.out.println("MessageId: " + publishResponse.getMessageId());
			}
		}
		
		S3Object fullObject = s3Client.getObject(new GetObjectRequest(bucketName, objectName));
		
        InputStreamReader isr3 = new InputStreamReader(fullObject.getObjectContent());
        displayTextInputStream(isr3);
		
		
	}
	
    private static void displayTextInputStream(InputStreamReader isr) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(isr);
        String line = null;
        int rows = 0;
        int size = 0;
        while ((line = reader.readLine()) != null) {
            //System.out.println(line);
        	size = size + line.length();  
            rows++;
           	System.out.println(line);
        }
        System.out.println("rows = " + rows +", size = " + size);
    }
}
