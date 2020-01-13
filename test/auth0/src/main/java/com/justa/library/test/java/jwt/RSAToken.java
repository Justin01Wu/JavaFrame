package com.justa.library.test.java.jwt;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
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
	// RSAToken has a big benefit, it has public key inside token , so client can verify it without security
	
	public static void main(String[] args) throws Exception {

		KeyPair kp = createRSAPair();
		
		long ttlMillis = 86400000l; // one day
		
		String token = createToken(12345, "Justa",  ttlMillis, kp);
		
		verifyToken(kp, token);
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

		Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);

		Date now = new Date(); // create time
		long expMillis = now.getTime() + ttlMillis;
		Date exp = new Date(expMillis); // expired time

		String token = JWT.create()
				.withExpiresAt(exp)
				.withKeyId("RSA_JUSTA" + now)
				.withIssuedAt(now)
				.withSubject(String.valueOf(userId)).withIssuer("JUSTA")
				.withClaim("userName", userName)
				.withClaim("justin", "I can add any fields into JWT token")
				.withClaim("email", "justin.wu@global.local")
				.withClaim("preferred_username", "justin.wu")
				.withClaim("realm_access", "offline_access")

				.sign(algorithm);
		return token;
	}
	
	public static DecodedJWT verifyToken(KeyPair kp, String token ) throws SecurityException, IOException, JwkException {
		
		RSAPublicKey publicKey = (RSAPublicKey)kp.getPublic();

	    Algorithm algorithm = Algorithm.RSA256(publicKey, null);  // we don't need private key if it is verifying
	    JWTVerifier verifier = JWT.require(algorithm)
	        .withIssuer("JUSTA")
	        .build(); //Reusable verifier instance
	    DecodedJWT jwt = verifier.verify(token);
	    
	    JwtUtil.printToken(jwt);

		return jwt;

	}

}
