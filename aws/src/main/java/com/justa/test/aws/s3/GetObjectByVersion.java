package com.justa.test.aws.s3;

import java.io.IOException;
import java.util.Iterator;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.VersionListing;

public class GetObjectByVersion {

	private static String bucketName = "justa-version";

	public static void main(String[] args) throws IOException {

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();

		ListVersionsRequest req = new ListVersionsRequest().withBucketName(bucketName);
		VersionListing versionList = s3Client.listVersions(req);
		S3VersionSummary target = null;
		while (true) {
			Iterator<S3VersionSummary> versionIter = versionList.getVersionSummaries().iterator();
			while (versionIter.hasNext()) {
				S3VersionSummary vs = versionIter.next();
				//s3Client.deleteVersion(bucketName, vs.getKey(), vs.getVersionId());
				System.out.println(" - " + vs.getKey() + ", version: " + vs.getVersionId() + " (size = " + vs.getSize() + ")");
				if(target == null) {
					target = vs;
				}
			}

			if (versionList.isTruncated()) {
				versionList = s3Client.listNextBatchOfVersions(versionList);
			} else {
				break;
			}
		}
		
		GetObjectRequest req2 = new GetObjectRequest(bucketName, target.getKey(), target.getVersionId()) ;

		S3Object obj = s3Client.getObject(req2);
		System.out.println("loaded:" + obj.getKey() 
			+" at version: " + obj.getObjectMetadata().getVersionId() 
			+" at time: " + obj.getObjectMetadata().getLastModified() 
			+ " at etag: " 	+ obj.getObjectMetadata().getETag());
		
	}
}
