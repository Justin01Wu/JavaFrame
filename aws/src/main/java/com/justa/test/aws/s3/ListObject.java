package com.justa.test.aws.s3;

import java.io.IOException;
import java.util.Iterator;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class ListObject {

	private static String bucketName = "justa";

	public static void main(String[] args) throws IOException {

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();
		
		listObjectSimple(s3Client);
		
		System.out.println(System.lineSeparator());
		listObjectComplicated(s3Client);

	}
	
	private static void listObjectComplicated(AmazonS3 s3Client) {
		ListObjectsV2Request req = new ListObjectsV2Request()
				.withBucketName(bucketName)
				.withPrefix("IMG")				
				.withMaxKeys(4);
		
		ListObjectsV2Result result;
		do {
			result = s3Client.listObjectsV2(req);
			for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
				System.out.println(" - " + objectSummary.getKey() + " " + " (size = " + objectSummary.getSize() + ")");
			}
			System.out.println("Next Continuation Token : " + result.getNextContinuationToken());
			req.setContinuationToken(result.getNextContinuationToken());
		} while (result.isTruncated() == true);
	}

	private static void listObjectSimple(AmazonS3 s3Client) {
		ObjectListing objectListing = s3Client.listObjects(bucketName);
		while (true) {
			Iterator<S3ObjectSummary> objIter = objectListing.getObjectSummaries().iterator();
			while (objIter.hasNext()) {
				S3ObjectSummary  sum = objIter.next();
				//s3Client.deleteObject(bucketName, objIter.next().getKey());
				System.out.println(" - " + sum.getKey() + " " + " (size = " + sum.getSize() + ")");
			}
			// If the bucket contains many objects, the listObjects() call
			// might not return all of the objects in the first listing. Check to
			// see whether the listing was truncated. If so, retrieve the next page of
			// objects  and delete them.
			if (objectListing.isTruncated()) {
				objectListing = s3Client.listNextBatchOfObjects(objectListing);
			} else {
				break;
			}
		}
	}
}
