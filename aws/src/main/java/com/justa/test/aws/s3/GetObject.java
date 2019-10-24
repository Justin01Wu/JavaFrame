package com.justa.test.aws.s3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
public class GetObject {
	
	private static Regions clientRegion = Regions.US_EAST_1;
	private static String bucketName = "justa";
	
    public static void main(String[] args) throws IOException {        
        
    	getFile(UploadObject.key2);
    	getFile(UploadObject.key1);

    }
    
    private static void getFile(String key) throws IOException{
        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    //.withCredentials(new ProfileCredentialsProvider())   
                    // this will ask user provide file C:\Users\[USERNAME]\.aws\credentials
                    // please see here for details  : https://stackoverflow.com/questions/41796355/aws-error-downloading-object-from-s3-profile-file-cannot-be-null/44079772
                    .build();
            /*
            By default, S3 objects are private, can only be accessed in AWS cloud or by login users
            In the code, you can set user secret in C:\Users\[USERNAME]\.aws\credentials
            	[default]
            	aws_access_key_id=dddkkdkd
            	aws_secret_access_key=sdkaddslda
            To make it easy, AWS also provide a tool kit for Eclipse. With that tool kit, you don’t need to set that file
            Also you don’t need set it in EC2 instance
            */

            // Get an object and print its contents.
            System.out.println("\r\n\r\n=====>Downloading an object: " + key);
            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
            System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
            
            System.out.println("MD5: " + fullObject.getObjectMetadata().getContentMD5());
            //MD5 is only meaningful during the transmission and its life cycle stops once the transmission is received and validated
            // so here, it is always null
            
            System.out.println("ETAG: " + fullObject.getObjectMetadata().getETag());
            // In some cases eTag = md5
            
            System.out.println("x-amz-meta-title: " + fullObject.getObjectMetadata().getUserMetaDataOf("x-amz-meta-title"));
            System.out.println("Content: ");
            InputStreamReader isr3 = new InputStreamReader(fullObject.getObjectContent());
            displayTextInputStream(isr3);

            // Get a range of bytes from an object and print the bytes.
            GetObjectRequest rangeObjectRequest = new GetObjectRequest(bucketName, key).withRange(0, 9);
            objectPortion = s3Client.getObject(rangeObjectRequest);
            System.out.println("Printing bytes retrieved.");

            InputStreamReader isr = new InputStreamReader(objectPortion.getObjectContent());
            displayTextInputStream(isr);

            // Get an entire object, overriding the specified response headers, and print the object's content.
            ResponseHeaderOverrides headerOverrides = new ResponseHeaderOverrides()
                    .withCacheControl("No-cache")
                    .withContentDisposition("attachment; filename=example.txt");
            GetObjectRequest getObjectRequestHeaderOverride = new GetObjectRequest(bucketName, key)
                    .withResponseHeaders(headerOverrides);
            headerOverrideObject = s3Client.getObject(getObjectRequestHeaderOverride);
            InputStreamReader isr2 = new InputStreamReader(headerOverrideObject.getObjectContent());
            displayTextInputStream(isr2);
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
            if (headerOverrideObject != null) {
                headerOverrideObject.close();
            }
        }

    }
    
    public static void displayTextInputStream(InputStreamReader isr) throws IOException {
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
