package scjp.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;


// from https://golb.hplar.ch/2017/10/JCE-policy-changes-in-Java-SE-8u151-and-8u152.html
public class TestJCE {
	
	public static void main(String[] args) 
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, 
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		Security.setProperty("crypto.policy", "unlimited");
		byte[] input = "My super secret text".getBytes();

		SecureRandom random = SecureRandom.getInstanceStrong();
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128, random);
		SecretKey key = keyGen.generateKey();
	
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "SunJCE");
		byte[] iv = new byte[12];
		random.nextBytes(iv);
		GCMParameterSpec spec = new GCMParameterSpec(256, iv);
		cipher.init(Cipher.ENCRYPT_MODE, key, spec);
		byte[] cipherText = cipher.doFinal(input);
	
		// Decrypt
		cipher.init(Cipher.DECRYPT_MODE, key, spec);
		byte[] plainText = cipher.doFinal(cipherText);
		System.out.println(new String(plainText));

		
	}

}
