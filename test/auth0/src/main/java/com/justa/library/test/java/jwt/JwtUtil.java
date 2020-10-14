package com.justa.library.test.java.jwt;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Date;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtUtil {

	public static final String JWT_SECRET = "sdtuO3495783490";

	private static final String CHARSET_NAME = "UTF-8";
	private static final String FIELD_NAME = "kid";
	private static final String ISS_FIELD_NAME = "iss";

	public static String createToken(Integer userId, String userName, long ttlMillis) {

		Date now = new Date(); // create time
		long expMillis = now.getTime() + ttlMillis;
		Date exp = new Date(expMillis); // expired time

		Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
		String token = JWT.create().withExpiresAt(exp).withKeyId("Jersey2_" + now).withIssuedAt(now)
				.withSubject(String.valueOf(userId)).withIssuer("Jersey2").withClaim("userName", userName)
				.withClaim("justin", "I can add any fields into JWT token").withClaim("email", "justin.wu@global.local")
				.withClaim("preferred_username", "justin.wu").withClaim("realm_access", "offline_access")

				.sign(algorithm);
		return token;
	}

	public static DecodedJWT verifyToken(String token) throws SecurityException, IOException, JwkException {

		
		//Algorithm algorithm = extractAlgorithm(token);
		Algorithm algorithm = extractAlgorithm2(token);
		
		Verification ver = JWT.require(algorithm);
		if(algorithm.getName().equals("HS256")) {
			ver = ver.withIssuer("Jersey2");
		}else {
			ver = ver.acceptExpiresAt(6678520957l);  // to skip expired verification
		}
		JWTVerifier verifier = ver.build(); // Reusable verifier instance
		DecodedJWT jwt = verifier.verify(token);

		return jwt;

	}

	private static Algorithm extractAlgorithm2(String token) throws SecurityException, IOException, JwkException {
		JWT parser =  new JWT ();	
		DecodedJWT jwt = parser.decodeJwt(token);
		String alg = jwt.getAlgorithm();
		
		if (alg.equals("HS256")) {
			return Algorithm.HMAC256(JWT_SECRET);
			
		}
		if (alg.equals("RS256")) {
			String kid = jwt.getClaim(FIELD_NAME).asString();
			String issuerUri = jwt.getClaim(ISS_FIELD_NAME).asString();
			
			Algorithm a = getRSA(kid, issuerUri);
			return a;
		}
		throw new RuntimeException("unknown Algorithm: " + alg );
	}
	
	/** @deprecated, please use extractAlgorithm2*/
	@Deprecated
	private static Algorithm extractAlgorithm(String token) throws SecurityException, IOException, JwkException {
	
		System.out.println("Attempting to extract algorithm information from token.");
		final String[] tokenParts = token.split("\\.");
		if (tokenParts.length != 3) { // Header + Body + Signature
			throw new SecurityException("Invalid token received.");
		}

		String base64EncodedHeader = tokenParts[0];
		String base64EncodedBody = tokenParts[1];

		String header = new String(Base64.getDecoder().decode(base64EncodedHeader.getBytes()), CHARSET_NAME);
		String body = new String(Base64.getDecoder().decode(base64EncodedBody.getBytes()), CHARSET_NAME);
		System.out.println(String.format("Decoded token's header: %s, and boy: %s", header, body));

		// The kid claim indicates the particular public key that was used to sign the
		// token
		new ObjectMapper();
		JsonNode headerNode = new ObjectMapper().readTree(header);
		JsonNode bodyNode = new ObjectMapper().readTree(body);

		String alg = headerNode.get("alg").textValue();
		
		if (alg.equals("HS256")) {
			Algorithm a = Algorithm.HMAC256(JWT_SECRET);
			return a;
		}

		String kid = headerNode.get(FIELD_NAME).textValue();
		String issuerUri = bodyNode.get(ISS_FIELD_NAME).textValue();
		
		Algorithm a = getRSA(kid, issuerUri);
		return a;
	}
	
	private static Algorithm getRSA(String kid, String issuerUri) throws JwkException, MalformedURLException {
		// Provider for retrieving public/private keys used by Microsoft to sign the
		// token
		String jwksUri;		
		if(issuerUri.equals("https://idp02.validusholdings.com:8443/auth/realms/vcaps-test")) {
			jwksUri = "https://idp02.validusholdings.com:8443/auth/realms/vcaps-test/protocol/openid-connect/certs";	
		}else {
			throw new SecurityException("unknown issuerUri: " + issuerUri);
		}

		
		JwkProvider provider = new UrlJwkProvider(new URL(jwksUri));
		/* this URL will return those information:
		{
			  "keys": [
			    {
			      "kid": "8UXJpSYq8I4_ziHX_LqVu393gCsXQFj-Jw03o7uXjJ4",
			      "kty": "RSA",
			      "alg": "RS256",
			      "use": "sig",
			      "n": "u7ZLRxTGyUX1SO-CZjNpUTU037weWUXpyBcppGTbv0EDtDzF1oZPBs1JOjDpFKOn4X5uHJl-VZkkBfRO5b6WwLwW-nSjsHM8pSc82rRbH6pcAU7-hJqKBIXFFSk3t9NIYADRjozaA1d4l83Iq42bCidvHaAcBNNgtR5huhFx3Iqax1msV7dNcxN1iFD8qBjQd436l791-XeRGfyrRLWoJS9cYsP8t2TDd8jy5wRgliwUFLJRI6yA_q9G5f3Q-1yRtl9_XR5yi1t8bj2aaKzzqvYw8S0EawAac5XQUB2sLPAKFqXAWOBhwj0nksSVAv2p-csjfikg0XUh9o5h3dMcOQ",
			      "e": "AQAB"
			    }
			  ]
			}
		*/

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

		// long ttlMillis = 3600000l; // one hour
		long ttlMillis = 86400000l; // one day
		// long ttlMillis = 31536000000l; // one year

		Integer userId = 1237;
		String token = JwtUtil.createToken(userId, "Justin.Wu", ttlMillis);

		System.out.println(token);

		DecodedJWT jwt = JwtUtil.verifyToken(token);
		printToken(jwt);

		// RAS token sample
		token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI4VVhKcFNZcThJNF96aUhYX0xxVnUzOTNnQ3NYUUZqLUp3MDNvN3VYako0In0.eyJqdGkiOiJmYjllOWY5Yi0wM2Y2LTRkNTItOGUxYS0zOGI5MGJlYjI3NDMiLCJleHAiOjE1Nzg1MjA5NTcsIm5iZiI6MCwiaWF0IjoxNTc4NTA2NTU3LCJpc3MiOiJodHRwczovL2lkcDAyLnZhbGlkdXNob2xkaW5ncy5jb206ODQ0My9hdXRoL3JlYWxtcy92Y2Fwcy10ZXN0IiwiYXVkIjoidmNhcHMtcWEtODA4NiIsInN1YiI6ImI3MTdhMDEzLTU0MDYtNDZjZC04YmU2LTkxYmI1YTU3Y2U5MyIsInR5cCI6IkJlYXJlciIsImF6cCI6InZjYXBzLXFhLTgwODYiLCJhdXRoX3RpbWUiOjE1Nzg0OTE5NzgsInNlc3Npb25fc3RhdGUiOiI3MDg1NzA3Ni1mODAwLTRhMjItYTBkNi05OTQ1Yjg5YmVhNDAiLCJhY3IiOiIwIiwiYWxsb3dlZC1vcmlnaW5zIjpbIioiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbInZjYXBzLWFkbWluIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sImF1dGhvcml6YXRpb24iOnsicGVybWlzc2lvbnMiOlt7InJzaWQiOiI4YmQwZWIwYi1lZWNkLTRiYTgtOGQ1Yy01YzVkNmIwYmJkZmYiLCJyc25hbWUiOiJEZWZhdWx0IFJlc291cmNlIn1dfSwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoianVzdGluLnd1IiwiZW1haWwiOiJqdXN0aW4ud3VAZ2xvYmFsLmxvY2FsIn0.j9leDHohvkQL0JDoVEO0zU_MMtFAqPlUcTfnq6XfXIHu7YlcbiHIpclHqEFHm08J0VAw2t58hPhmqJZ9Q4pWpgKUyM-okXlK5mw4Phez25Yamo0Ztko9agACvVCbhd6c7fBW5ysKbLrgMDOtX_2QoVg0MGF9eRATG1KUpD0fHeukyM50-zLz1RQXRAK77WsQsyLf4GeBDg_evD71D1LdmbA5RYIEBPc7Kng_Xh0JpEnVs30a6x-8pPIEgp3zFtfsl-ZAB_igJoKSoB7RLp2dDi1rifU0rGwQVn94PbkYiRXoaKvJJlthuJ-KHLszkMxiMM5D-YLJKGiMgXdwSiXh5Q";
		jwt = JwtUtil.verifyToken(token);
		printToken(jwt);
	}

}
