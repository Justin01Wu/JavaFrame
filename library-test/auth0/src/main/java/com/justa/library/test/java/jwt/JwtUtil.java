package com.justa.library.test.java.jwt;

import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Date;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {
	
	public static final String JWT_SECRET = "sdtuO3495783490";
	
	
	private static final String CHARSET_NAME = "UTF-8";
  private static final String FIELD_NAME = "kid";
	  private static final String ISS_FIELD_NAME = "iss";
	  
	public static String createToken(Integer userId, String userName, long ttlMillis) {

		Date now = new Date();  		// create time
		long expMillis = now.getTime() + ttlMillis;
		Date exp = new Date(expMillis); // expired time

		Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
		String token = JWT.create()
				.withExpiresAt(exp)
				.withKeyId("Jersey2_"+ now)
				.withIssuedAt(now)
				.withSubject(String.valueOf(userId))
				.withIssuer("Jersey2")
				.withClaim("userName", userName)
				.withClaim("justin", "I can add any fields into JWT token")
				.withClaim("email", "justin.wu@global.local")
				.withClaim("preferred_username", "justin.wu")
				.withClaim("realm_access", "offline_access")
				
				.sign(algorithm);
		return token;
	}


	
	public static DecodedJWT verifyToken(String token) throws SecurityException, IOException, JwkException {
		
	    //Algorithm algorithm = Algorithm.HMAC256(JWTSetting.JWT_SECRET);
	    Algorithm algorithm = extractAlgorithm(token);
	    
	    JWTVerifier verifier = JWT.require(algorithm)
	        .withIssuer("Jersey2")
	        .build(); //Reusable verifier instance
	    DecodedJWT jwt = verifier.verify(token);
		
		return jwt;
		
		
	}
	
	  private static Algorithm extractAlgorithm( String token)
	      throws SecurityException, IOException, JwkException {
	    System.out.println("Attempting to extract algorithm information from token.");
	    final String[] tokenParts = token.split("\\.");
	    if (tokenParts.length != 3) { // Header + Body + Signature
	      throw new SecurityException("Invalid token received.");
	    }

	    String base64EncodedHeader = tokenParts[0];
	    String base64EncodedBody = tokenParts[1];

	    String header = new String(Base64.getDecoder().decode(base64EncodedHeader.getBytes()),
	        CHARSET_NAME);
	    String body = new String(Base64.getDecoder().decode(base64EncodedBody.getBytes()),
	        CHARSET_NAME);
	    System.out.println(String.format("Decoded token's header: %s, and boy: %s", header, body));

	    // The kid claim indicates the particular public key that was used to sign the token
	    JsonNode headerNode = new ObjectMapper().readTree(header);
	    JsonNode bodyNode = new ObjectMapper().readTree(body);

	    String alg = headerNode.get("alg").textValue();
	    Algorithm a = Algorithm.HMAC256(JWT_SECRET);
	    if(a.getName().equals(alg)){
	    	return a;
	    }	    
	    
	    String kid = headerNode.get(FIELD_NAME).textValue();
	    String issuerUri = bodyNode.get(ISS_FIELD_NAME).textValue();
	    // Provider for retrieving public/private keys used by Microsoft to sign the token
//	    Optional<String> jwksUri = trustedIdps.getJwksUriByIssuerUri(issuerUri);
//	    if (!jwksUri.isPresent()) {
//	      throw new SecurityException(
//	          String.format("No mapping jwksUri found for issuerUri: %s", issuerUri));
//	    }
//	    JwkProvider provider = new UrlJwkProvider(new URL(jwksUri.get()));
	    
	    JwkProvider provider = new UrlJwkProvider(new URL(issuerUri));
	    
	    Jwk jwk = provider.get(kid);

	    // Get the correct algorithm for decoding
	    return Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
	  }
	
	public static void printToken(DecodedJWT jwt) {
		System.out.println("subject=" + jwt.getSubject());
		System.out.println("keyId=" + jwt.getKeyId());
		System.out.println("Id=" + jwt.getId());
		System.out.println("issuer=" + jwt.getIssuer());
		
		// customized field
		System.out.println("userName=" + jwt.getClaim("userName").asString());
		System.out.println("justin=" + jwt.getClaim("justin").asString());
		
	}
	
	public static void main(String[] args) throws Exception {
		
		//long ttlMillis = 3600000l;  // one hour
		long ttlMillis = 86400000l;   // one day
		//long ttlMillis = 31536000000l;  // one year
		
		Integer userId = 1237;
		String token = JwtUtil.createToken(userId, "Justin.Wu", ttlMillis);
		
		System.out.println(token);
		
		DecodedJWT jwt = JwtUtil.verifyToken(token);
		printToken(jwt);
	}
	
	

}
