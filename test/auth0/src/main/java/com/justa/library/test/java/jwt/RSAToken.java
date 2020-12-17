package com.justa.library.test.java.jwt;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import com.auth0.jwk.JwkException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

// from https://blog.miguelgrinberg.com/post/json-web-tokens-with-public-key-signatures
//  https://wstutorial.com/misc/jwt-java-public-key-rsa.html
//  https://www.novixys.com/blog/how-to-generate-rsa-keys-java/
public class RSAToken {
	// RSAToken has a big benefit, it has public key inside token, so client can verify it without secret	
	// Also client can get public key from issuer on OpenId specification:  https://ldapwiki.com/wiki/Openid-configuration
	// Above ways are not safe way, because other people can create their own RSA key and pass it to you.
	// So usually it must combine with white list	
	public static void main(String[] args) throws Exception {

		KeyPair kp = createRSAPair();
		
		long ttlMillis = 86400000l; // one day
		
		String token = createToken(12345, "Justa",  ttlMillis, kp);
		System.out.println(token);
		
		//verifyToken(kp, token);
		verifyToken(token);  
		// because public key is already inside the token, so we don't need to pass it in 
	}
	
	public static KeyPair createRSAPair() throws NoSuchAlgorithmException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		
		kpg.initialize(2048);
		KeyPair kp = kpg.generateKeyPair();
		return kp;
		
	}
	
	public static String createToken(Integer userId, String userName, long ttlMillis, KeyPair kp) {

		RSAPublicKey publicKey = (RSAPublicKey)kp.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey)kp.getPrivate();
		byte[] pk = publicKey.getEncoded();
		String encodedPublicKey = Base64.getEncoder().encodeToString(pk);
		System.out.println("Public Key:");
		System.out.println(convertToPublicKey(encodedPublicKey));
		
		Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);

		Date now = new Date(); // create time
		long expMillis = now.getTime() + ttlMillis;
		Date exp = new Date(expMillis); // expired time

		String token = JWT.create()
				.withExpiresAt(exp)
				.withKeyId("RSA_JUSTA_" + now.getTime())
				.withIssuedAt(now)
				.withSubject(String.valueOf(userId)).withIssuer("JUSTA")
				.withClaim("publicKey", encodedPublicKey)
				// add public Key into token , client side can verify it without secrets
				.withClaim("userName", userName)
				.withClaim("justin", "I can add any fields into JWT token")
				.withClaim("email", "justin.wu@global.local")
				.withClaim("preferred_username", "justin.wu")
				.withClaim("realm_access", "offline_access")

				.sign(algorithm);
		return token;
	}
	
	// Add BEGIN and END comments
	private static String convertToPublicKey(String key) {
		StringBuilder result = new StringBuilder();
		result.append("-----BEGIN PUBLIC KEY-----\n");
		result.append(key);
		result.append("\n-----END PUBLIC KEY-----");
		return result.toString();
	}
	
	private static RSAPublicKey convertFromPublicKey(String pubKeyPEM) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] encodedPublicKey = Base64.getDecoder().decode(pubKeyPEM);

	    X509EncodedKeySpec spec = new X509EncodedKeySpec(encodedPublicKey);
	    KeyFactory kf = KeyFactory.getInstance("RSA");
	    RSAPublicKey pk = (RSAPublicKey)kf.generatePublic(spec);
	    System.out.println(pk);
	    return pk;
	}
	
	public static DecodedJWT verifyToken(
			//KeyPair kp, 
			String token ) throws SecurityException, IOException, JwkException, NoSuchAlgorithmException, InvalidKeySpecException {
		
		JWT parser =  new JWT ();	
		DecodedJWT jwt = parser.decodeJwt(token);
		String pk = jwt.getClaim("publicKey").asString();
		RSAPublicKey publicKey = convertFromPublicKey(pk);
		
		//RSAPublicKey publicKey = (RSAPublicKey)kp.getPublic();

	    Algorithm algorithm = Algorithm.RSA256(publicKey, null);  // we don't need private key if it is verifying
	    JWTVerifier verifier = JWT.require(algorithm)
	        .withIssuer("JUSTA")
	        .build(); //Reusable verifier instance
	    jwt = verifier.verify(token);
	    
	    JwtUtil.printToken(jwt);

		return jwt;

	}

}
