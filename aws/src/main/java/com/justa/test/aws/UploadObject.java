package com.justa.test.aws;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;


// it comes from https://docs.aws.amazon.com/AmazonS3/latest/dev/UploadObjSingleOpJava.html
public class UploadObject {
	
	private static String bucketName = "justa";
	private static Regions clientRegion = Regions.US_EAST_1;
	
	public static void main(String[] args) throws IOException {        
        
        //This code expects that you have AWS credentials set up per:
        // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();

        // Upload a text string as a new object.
        String stringObjKeyName = "UploadObjectFromString.txt";
        String content = "Uploaded String Object at: " + new Date();
        s3Client.putObject(bucketName, stringObjKeyName, content);

        // Upload a file as a new object with ContentType and title specified.
        updateAFile(s3Client);
        System.out.println("Done");
    }
	
	private static void updateAFile(AmazonS3 s3Client) {
        String fileObjKeyName = "UploadObjectFromFile.txt";
        String fileName =   UploadObject.class.getClassLoader().getResource("aws-s3-sample.txt").getFile();
        PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("plain/text");
        metadata.addUserMetadata("x-amz-meta-title", "someTitle");
        request.setMetadata(metadata);
        s3Client.putObject(request);
		
	}
}
