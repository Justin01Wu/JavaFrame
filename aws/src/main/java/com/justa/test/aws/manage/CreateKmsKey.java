package com.justa.test.aws.manage;

import java.io.IOException;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.CreateKeyRequest;

// from official guide exercise 5.3
public class CreateKmsKey {

	public static void main(String[] args) throws IOException {

		AWSKMS kmsClient = AWSKMSClientBuilder.standard().build();
		
		CreateKeyRequest req =  new CreateKeyRequest()
				.withOrigin("AWS_KMS")
				.withDescription("My KMS Key")
				.withKeyUsage("ENCRYPT_DECRYPT");
		kmsClient.createKey(req);

	}
}
