package com.justa.test.aws.s3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.SSEAwsKeyManagementParams;
import com.amazonaws.services.s3.model.StorageClass;
import com.amazonaws.services.s3.model.Tag;
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
        PutObjectResult  result = updateAFile(s3Client);
        System.out.println("Md5 = " + result.getContentMd5());  
        System.out.println("Etag = " + result.getETag());
        // both md5 and etag based on file content, won't be affected any others, like tag or user meta data
        
        System.out.println("VersionId = " + result.getVersionId());
        
        System.out.println("Done");
    }
	
	private static PutObjectResult updateAFile(AmazonS3 s3Client) throws IOException {
        String fileObjKeyName = key1;
        String fileName =   UploadObject.class.getClassLoader().getResource(key1).getFile();
        File file = new File(fileName);        

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("plain/text");
        
        String md5 = generateMd5(file);
        metadata.setContentMD5(md5);
        //MD5 is only meaningful during the transmission and its life cycle stops once the transmission is received and validated
        
        PutObjectRequest req = new PutObjectRequest(bucketName, fileObjKeyName, file)
        		.withMetadata(metadata)
        		.withCannedAcl(CannedAccessControlList.PublicRead)      // set target file public
        		.withSSEAwsKeyManagementParams(new SSEAwsKeyManagementParams())  // use default S3 key to encrypt data
        		//.withSSEAwsKeyManagementParams(new SSEAwsKeyManagementParams("d13f8114-47c3-4263-ad47-bf3d5f7fe4bb"))  // use dedicated AWS managed KeyId
        		// in those way, target s3 file will be marked as "AWS-KMS
        		.withStorageClass(StorageClass.OneZoneInfrequentAccess);
        
        // add tags
        metadata.addUserMetadata("x-amz-meta-title", "someTitle12345123");
        List<Tag> tags = new ArrayList<Tag>();
        tags.add(new Tag("department", "developer"));
        tags.add(new Tag("archieveRule", "quick"));
        req.setTagging(new ObjectTagging(tags));
        
        
        PutObjectResult  result = s3Client.putObject(req);
        return result;
		
	}
	
	private static String generateMd5(File file) throws IOException {
		
		FileInputStream fis = new FileInputStream(file);
		byte[] content_bytes = IOUtils.toByteArray(fis);
		String md5 = new String(Base64.getEncoder().encode(DigestUtils.md5(content_bytes)));
		System.out.println(md5);
		return md5;
	}
	
	
}
