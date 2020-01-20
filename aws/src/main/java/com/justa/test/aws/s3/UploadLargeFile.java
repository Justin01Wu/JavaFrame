package com.justa.test.aws.s3;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

// https://www.baeldung.com/aws-s3-multipart-upload
// Without multiPart, the 700MB file need 1 minute 18 seconds in EC2
// With multiPart on 5MB batch size, the 700MB file need 1 minute 03 seconds in EC2

/* TODO try concurrency uploading:
 Amazon S3 does not currently support object locking. If two PUT requests
are simultaneously made to the same key, the request with the latest time
stamp wins. If this is an issue, you will be required to build an object locking
mechanism into your application.
 */

public class UploadLargeFile {
	private static String bucketName = "justa";
	public static String keyName = "master-iter-NorthAmTorHail-v18.txt";
	
	public static void main(String[] args) throws IOException, AmazonServiceException, AmazonClientException, InterruptedException {
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().build();
        
        File file = new File("C:\\TEMP\\aws_test\\master-iter-NorthAmTorHail-v18.txt");
        
        System.out.println(new Date());
        if(args.length > 0 && args[1].equals("single")) {
        	System.out.println("single method");
            PutObjectRequest req = new PutObjectRequest(bucketName, keyName, file);
            amazonS3.putObject(req);        	
        }else {            
        	System.out.println("Multipart method");
            TransferManager tm = TransferManagerBuilder.standard()
          		  .withS3Client(amazonS3)
          		  .withMultipartUploadThreshold((long) (5 * 1024 * 1025))
          		  .build();
          
          
          Upload upload = tm.upload(bucketName, keyName, file);
          
          upload.waitForCompletion();

        }
        
        
        System.out.println(new Date());
	}
}
