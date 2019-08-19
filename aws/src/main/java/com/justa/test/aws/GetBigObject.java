package com.justa.test.aws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.S3Object;

//  for non-public file access, please set env variable AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY  
// see here for details:https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html 
public class GetBigObject {

    public static void main(String[] args) throws IOException {
    	
    	if(args.length != 2) {
    		System.out.println("expected two arguments: filename and method[Single|Range]");
    		return;
    	}
    	if(!args[1].equals("Single") && !args[1].equals("Range")) {
    		System.out.println("unexpected method, must be Single or Range");
    		return;    		
    	}
    	
    	System.out.println(new Date());
        Regions clientRegion = Regions.US_EAST_1;
        String bucketName = "justa";
        String key = args[0];  

        S3Object fullObject = null, objectPortion = null;
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    //.withCredentials(new ProfileCredentialsProvider())   
                    // this will ask user provide file C:\Users\[USERNAME]\.aws\credentials
                    // please see here for details  : https://stackoverflow.com/questions/41796355/aws-error-downloading-object-from-s3-profile-file-cannot-be-null/44079772
                    .build();
            
            System.out.println(new Date());

            if(args[1].equals("Single")) {
                // Get an object and print its contents.
                System.out.println("Downloading an object");
                System.out.println(new Date());
                fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
                System.out.println(new Date());
                System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
                System.out.println(new Date());
                System.out.println("Content: ");
                InputStreamReader isr = new InputStreamReader(fullObject.getObjectContent());
                GetLocalFile.displayTextInputStream(isr);
                System.out.println(new Date());
            }else {
                // Get a range of bytes from an object and print the bytes.
            	System.out.println(new Date());
                GetObjectRequest rangeObjectRequest = new GetObjectRequest(bucketName, key).withRange(30000000, 90000000);  //about 60mb from 700mb
                System.out.println(new Date());
                objectPortion = s3Client.getObject(rangeObjectRequest);
                System.out.println(new Date());
                System.out.println("Printing bytes retrieved.");
                InputStreamReader isr = new InputStreamReader(objectPortion.getObjectContent());
                GetLocalFile.displayTextInputStream(isr);
                System.out.println(new Date());
            	
            }

        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        } finally {
            // To ensure that the network connection doesn't remain open, close any open input streams.
            if (fullObject != null) {
                fullObject.close();
            }
            if (objectPortion != null) {
                objectPortion.close();
            }
        }
    }

}
