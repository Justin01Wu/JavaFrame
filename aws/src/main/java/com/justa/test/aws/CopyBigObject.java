package com.justa.test.aws;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

//  for non-public file access, please set env variable AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY  
// see here for details:https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html 
public class CopyBigObject {

    public static void main(String[] args) throws IOException {
    	
    	if(args.length != 1) {
    		System.out.println("expected one arguments: filename");
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

            // Get an object and print its contents.
            System.out.println("Downloading an object");
            System.out.println(new Date());
            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
            System.out.println(new Date());
            System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
            System.out.println(new Date());
            System.out.println("copying: ");
            writeToFile(key, fullObject.getObjectContent());
            System.out.println(new Date());

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
    
    private static void writeToFile(String fileName, InputStream inputStream) throws IOException {
    	File targetFile = new File(fileName);
        byte[] buffer = new byte[8 * 1024];
        int bytesRead;
        int amount = 0;
		try(OutputStream outStream = new FileOutputStream(targetFile)){
	        while ((bytesRead = inputStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	            amount ++ ;
	            if(amount% 5000 ==0 ) {
	            	System.out.println("procession 8k data at " + amount );
	            }
	        }			
		}
    }


}
