package scjp.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESFileEncryption {
	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding"; // CBC mode with PKCS5Padding

	public static void encryptFile(File inputFile, File outputFile, SecretKey secretKey, IvParameterSpec iv)
			throws Exception {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		try (FileInputStream inputStream = new FileInputStream(inputFile);
				CipherOutputStream outputStream = new CipherOutputStream(new FileOutputStream(outputFile), cipher)) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}
	}

	public static void decryptFile(File inputFile, File outputFile, SecretKey secretKey, IvParameterSpec iv)
			throws Exception {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		try (CipherInputStream inputStream = new CipherInputStream(new FileInputStream(inputFile), cipher);
				FileOutputStream outputStream = new FileOutputStream(outputFile)) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}
	}

	public static void main(String[] args) {

		if (args.length != 3) {
			System.out.println("This program need 3 arguments: E/D origFilePath securityKey");
			return;
		}
		try {
			// 1. Generate AES Key
			KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
			keyGen.init(256); // 128, 192, or 256 bits			
			
			SecretKey secretKey = getKeyFromPassword(args[2], "mySalt123!");

			// 2. Generate IV (Initialization Vector)
			SecureRandom secureRandom = new SecureRandom();
			byte[] ivBytes = new byte[16]; // 16 bytes for AES block size
			secureRandom.nextBytes(ivBytes);
			IvParameterSpec iv = new IvParameterSpec(ivBytes);

			// 3. Define input file
			File originalFile = new File(args[1]);

			if (originalFile.exists() == false) {
				System.out.println(
						"Original file does not exist. Please check the path: " + originalFile.getAbsolutePath());
				return;
			} else {
				System.out.println("Original file found: " + originalFile.getAbsolutePath());
			}

			if (args[0].equals("E")) {
				File encryptedFile = new File(args[1] + ".enc");

				System.out.println("Encrypting file...");
				encryptFile(originalFile, encryptedFile, secretKey, iv);
				System.out.println("File encrypted successfully:" + encryptedFile.getAbsolutePath());
			} else if (args[0].equals("D")) {
				File decryptedFile = new File(args[1] + ".dec");
				System.out.println("Decrypting file...");
				decryptFile(originalFile, decryptedFile, secretKey, iv);
				System.out.println("File decrypted successfully: " + decryptedFile.getAbsolutePath());
			} else {
				System.out.println("Invalid operation. Use 'E' for encrypt or 'D' for decrypt.");
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SecretKey getKeyFromPassword(String password, String salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
		SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
		return secret;
	}

}
