package com.justa.test.aws.s3;

import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.amazonaws.services.s3.AmazonS3Encryption;
import com.amazonaws.services.s3.AmazonS3EncryptionClientBuilder;
import com.amazonaws.services.s3.model.CryptoConfiguration;
import com.amazonaws.services.s3.model.CryptoMode;
import com.amazonaws.services.s3.model.EncryptionMaterials;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.StaticEncryptionMaterialsProvider;
import com.amazonaws.util.IOUtils;

// from https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-crypto-masterkey.html
// Amazon S3 Client-Side Encryption with Client Master Keys
public class TestClientMasterKey {
	
	private static AmazonS3Encryption encryptionClient;

	public static void main(String[] args) throws Exception {
		String bucketName = "justa";
		String objectKey = "FileEncrupedOnClientMasterKey";
		
		SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
		// TODO need to save secretKey for next time decrypting.
		
		
		StaticEncryptionMaterialsProvider materialProvider = new StaticEncryptionMaterialsProvider(new EncryptionMaterials(secretKey));
		CryptoConfiguration cryptoConfig= new CryptoConfiguration(CryptoMode.EncryptionOnly);
		
		encryptionClient = AmazonS3EncryptionClientBuilder.standard()
		.withEncryptionMaterials(materialProvider)
		.withCryptoConfiguration(cryptoConfig)
		.build();

		// Upload object using the encryption client.
		String plaintext = "Hello World, S3 Client-side Encryption Using Asymmetric Master Key!";

		encryptionClient.putObject(bucketName, objectKey, plaintext);

		// Download the object.
		S3Object downloadedObject = encryptionClient.getObject(bucketName, objectKey);
		// asymmetric key , so use the same key
		// the ENCRYPTED_KEY is objectKey? risky? 
		
		byte[] decrypted = IOUtils.toByteArray(downloadedObject.getObjectContent());
		// Verify same data.
		if(Arrays.equals(plaintext.getBytes(), decrypted)) {
			System.out.println("decrupted successfully");
		}else{
			System.err.println("decrupted failed");
		};
	}
}
