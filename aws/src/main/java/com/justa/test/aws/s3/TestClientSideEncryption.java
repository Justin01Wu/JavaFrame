package com.justa.test.aws.s3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.amazonaws.services.s3.AmazonS3Encryption;
import com.amazonaws.services.s3.AmazonS3EncryptionClientBuilder;
import com.amazonaws.services.s3.model.CryptoConfiguration;
import com.amazonaws.services.s3.model.CryptoMode;
import com.amazonaws.services.s3.model.EncryptionMaterials;
import com.amazonaws.services.s3.model.StaticEncryptionMaterialsProvider;

// from https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-crypto-masterkey.html
// Amazon S3 Client-Side Encryption with Client Master Keys
// it is transparent for the server, so Server side will mark this file as "No Encryption"
public class TestClientSideEncryption {
	
	private static AmazonS3Encryption encryptionClient;
	
	// Upload object using the encryption client.
	public static final String plaintext = "Hello World, S3 Client-side Encryption Using Asymmetric Master Key!";
	public static final String objectKey = "FileEncrupedOnClientMasterKey";
	public static final String  bucketName = "justa";

	public static void main(String[] args) throws Exception {
		
		KeyGenerator gen = KeyGenerator.getInstance("AES");
		gen.init(256, new SecureRandom());
		SecretKey secretKey = gen.generateKey();
		
		System.out.println(secretKey.getAlgorithm());
		System.out.println(secretKey.getFormat());
		System.out.println(secretKey.getClass().getName());
		saveAESKey(secretKey);
		// save secretKey for next time decryption
		// it is the master key of envelope Encryption
		// S3 service will randomly generate data key and use this to encrypt it
		// the final data has encrypted data key + encrypted data
		
		StaticEncryptionMaterialsProvider materialProvider = new StaticEncryptionMaterialsProvider(new EncryptionMaterials(secretKey));
		CryptoConfiguration cryptoConfig= new CryptoConfiguration(CryptoMode.EncryptionOnly);
		
		encryptionClient = AmazonS3EncryptionClientBuilder.standard()
		.withEncryptionMaterials(materialProvider)
		.withCryptoConfiguration(cryptoConfig)
		.build();

		encryptionClient.putObject(bucketName, objectKey, plaintext);
		
		System.out.println("  ==>send dercypted data succeefully");


	}
	
	private static void saveAESKey(SecretKey secretKey) throws FileNotFoundException, IOException {
        File file = new File("target/AES_key.txt");
        
        try(FileOutputStream fos = new FileOutputStream(file)) {             
             
            fos.write(secretKey.getEncoded());
 
        }
	}
}
