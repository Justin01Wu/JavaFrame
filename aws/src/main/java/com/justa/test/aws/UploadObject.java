package com.justa.test.aws;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;


// it comes from https://docs.aws.amazon.com/AmazonS3/latest/dev/UploadObjSingleOpJava.html
public class UploadObject {
	
	private static String bucketName = "justa";
	private static Regions clientRegion = Regions.US_EAST_1;
	public static String key1 = "aws-s3-sample.txt";
	public static String key2 = "UploadObjectFromString.txt";
	
	public static void main(String[] args) throws IOException {
		
		//  for security 
		// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html 
		// please set env variable AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY
		
        
        //This code expects that you have AWS credentials set up per:
        // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();

        // Upload a text string as a new object.
        String stringObjKeyName = key2;
        String content = "Uploaded String Object at: " + new Date();
        s3Client.putObject(bucketName, stringObjKeyName, content);

        // Upload a file as a new object with ContentType and title specified.
        updateAFile(s3Client);
        System.out.println("Done");
    }
	
	private static void updateAFile(AmazonS3 s3Client) throws IOException {
        String fileObjKeyName = key1;
        String fileName =   UploadObject.class.getClassLoader().getResource(key1).getFile();
        File file = new File(fileName);        

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("plain/text");
        
        String md5 = generateMd5(file);
        metadata.setContentMD5(md5);
        metadata.addUserMetadata("x-amz-meta-title", "someTitle");
        
        PutObjectRequest req = new PutObjectRequest(bucketName, fileObjKeyName, file).withMetadata(metadata);
        s3Client.putObject(req);
		
	}
	
	private static String generateMd5(File file) throws IOException {
		
		FileInputStream fis = new FileInputStream(file);
		byte[] content_bytes = IOUtils.toByteArray(fis);
		String md5 = new String(Base64.getEncoder().encode(DigestUtils.md5(content_bytes)));
		System.out.println(md5);
		return md5;
	}
	
	
}
