package com.justa.test.aws.s3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.amazonaws.services.s3.AmazonS3Encryption;
import com.amazonaws.services.s3.AmazonS3EncryptionClientBuilder;
import com.amazonaws.services.s3.model.CryptoConfiguration;
import com.amazonaws.services.s3.model.CryptoMode;
import com.amazonaws.services.s3.model.EncryptionMaterials;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.StaticEncryptionMaterialsProvider;
import com.amazonaws.util.IOUtils;

//from https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-crypto-masterkey.html
// you can manually decrypt the data, but in this way, S3Client automatically decrypt the data
public class TestClientSideDecryption {

	private static AmazonS3Encryption encryptionClient;

	public static void main(String[] args) throws IOException {

		byte[] aesKeyRaw = readAesKey();  //saved from TestClientSideEncryption
		
		SecretKey aesKey = new SecretKeySpec(aesKeyRaw, "AES");		
		
		StaticEncryptionMaterialsProvider materialProvider = new StaticEncryptionMaterialsProvider(new EncryptionMaterials(aesKey));
		CryptoConfiguration cryptoConfig= new CryptoConfiguration(CryptoMode.EncryptionOnly);
		
		encryptionClient = AmazonS3EncryptionClientBuilder.standard()
		.withEncryptionMaterials(materialProvider)
		.withCryptoConfiguration(cryptoConfig)
		.build();

		// Download the object.
		S3Object downloadedObject = encryptionClient.getObject(TestClientSideEncryption.bucketName, TestClientSideEncryption.objectKey);
		// asymmetric key , so use the same s3 client
		
		byte[] decrypted = IOUtils.toByteArray(downloadedObject.getObjectContent());
		// Verify same data.
		if(Arrays.equals(TestClientSideEncryption.plaintext.getBytes(), decrypted)) {
			System.out.println("decrupted successfully");
		}else{
			System.err.println("decrupted failed");
		};
		System.out.println(new String(decrypted));
	}

	private static byte[] readAesKey() throws IOException {
		File file = new File("target/AES_key.txt");
		try (FileInputStream fin = new FileInputStream(file)) {

			byte fileContent[] = new byte[(int) file.length()];

			// Reads up to certain bytes of data from this input stream into an array of
			// bytes.
			fin.read(fileContent);
			return fileContent;
		}
	}
}
