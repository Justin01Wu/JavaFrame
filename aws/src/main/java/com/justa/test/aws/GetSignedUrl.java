package com.justa.test.aws;

import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

// https://docs.aws.amazon.com/AmazonS3/latest/dev/ShareObjectPreSignedURLJavaSDK.html
public class GetSignedUrl {
	
	public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException {
	        
	        Regions clientRegion = Regions.US_EAST_1;
	        String bucketName = "justa";
	        String objectKey = "proverb.html";

	        try {
	            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
	                    .withRegion(clientRegion)
	                    //.withCredentials(new ProfileCredentialsProvider())
	                    .build();

	            // Set the presigned URL to expire after 5 minutes.
	            java.util.Date expiration = new java.util.Date();
	            long expTimeMillis = expiration.getTime();
	            expTimeMillis += 1000 * 60 * 5;
	            expiration.setTime(expTimeMillis);

	            // Generate the presigned URL.
	            System.out.println("Generating pre-signed URL.");
	            GeneratePresignedUrlRequest generatePresignedUrlRequest =
	                    new GeneratePresignedUrlRequest(bucketName, objectKey)
	                            .withMethod(HttpMethod.GET)
	                            .withExpiration(expiration);
	            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

	            System.out.println("Pre-Signed URL: " + url.toString());
	        } catch (AmazonServiceException e) {
	            // The call was transmitted successfully, but Amazon S3 couldn't process 
	            // it, so it returned an error response.
	            e.printStackTrace();
	        } catch (SdkClientException e) {
	            // Amazon S3 couldn't be contacted for a response, or the client
	            // couldn't parse the response from Amazon S3.
	            e.printStackTrace();
	        }
	}
}
