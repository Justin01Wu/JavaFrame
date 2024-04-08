package com.justa.test.aws.manage;

import java.io.IOException;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DescribeKeyRequest;
import com.amazonaws.services.kms.model.DescribeKeyResult;
import com.amazonaws.services.kms.model.KeyListEntry;
import com.amazonaws.services.kms.model.ListKeysResult;

//from official guide exercise 5.3
// print all AWS managed keys and customer managed keys
public class ListKmsKey {
	
	public static void main(String[] args) throws IOException {

		AWSKMS kmsClient = AWSKMSClientBuilder.standard().build();
		
		ListKeysResult result =  kmsClient.listKeys();
		
		for(KeyListEntry entry : result.getKeys()) {
			System.out.println(entry);	
			DescribeKeyRequest req = new DescribeKeyRequest().withKeyId(entry.getKeyId());
			DescribeKeyResult dResult = kmsClient.describeKey(req);
			System.out.println("state = "  + dResult.getKeyMetadata().getKeyState());	
			System.out.println("desc = "  + dResult.getKeyMetadata().getDescription());
			System.out.println("usage = "  + dResult.getKeyMetadata().getKeyUsage());
			System.out.println("manager = "  + dResult.getKeyMetadata().getKeyManager());
			System.out.println("enabled = "  + dResult.getKeyMetadata().getEnabled());
			System.out.println("created = "  + dResult.getKeyMetadata().getCreationDate());
			
			System.out.println();	
		}
		

	}
}
