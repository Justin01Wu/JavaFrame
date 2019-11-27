package com.justa.test.aws.manage;

import java.io.IOException;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
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
		}
		

	}
}
