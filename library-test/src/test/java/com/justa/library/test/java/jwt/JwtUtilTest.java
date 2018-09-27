package com.justa.library.test.java.jwt;

import org.junit.Test;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

public class JwtUtilTest {

	@Test(expected = TokenExpiredException.class)
	public void testExpiredToken() {		
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM3IiwiaXNzIjoiSmVyc2V5MiIsImp1c3RpbiI6IkkgY2FuIGFkZCBhbnkgZmllbGRzIGludG8gSldUIHRva2VuIiwiZXhwIjoxNTM4MDU2NDI5LCJpYXQiOjE1MzgwNTY0MjgsInVzZXJuYW1lIjoiSnVzdGluLld1In0.lbt80vhVFijT_3jL68Q8ofbWLStu4ikM8JFe2LvPxpw";
		
		JwtUtil.verifyToken(token);

	}
	
	@Test(expected = SignatureVerificationException.class)
	public void testWrongSignature() {		
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";
		
		JwtUtil.verifyToken(token);  // The Token's Signature resulted invalid when verified using the Algorithm: HmacSHA256


	}	
	
	@Test
	public void testValidToken() {		
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM3IiwiaXNzIjoiSmVyc2V5MiIsImp1c3RpbiI6IkkgY2FuIGFkZCBhbnkgZmllbGRzIGludG8gSldUIHRva2VuIiwiZXhwIjoxNTk4MDU2NjgzLCJpYXQiOjE1MzgwNTY2ODMsInVzZXJuYW1lIjoiSnVzdGluLld1In0.CF340F11Dmxr83cPEElkZ3Ka_qLjzvArnrb-HToFjbU";
		
		JwtUtil.verifyToken(token);

	}
	
	@Test
	public void testNewTokenIsValid() {		
	
		String token = JwtUtil.createToken(1237, "Justin.Wu", 60000000000l);
		System.out.println(token);
		
		JwtUtil.verifyToken(token);
	}
	
	@Test(expected = InvalidClaimException.class)
	public void testWrongIssuer() {		
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM3IiwiaXNzIjoiSmVyc2V5IiwianVzdGluIjoiSSBjYW4gYWRkIGFueSBmaWVsZHMgaW50byBKV1QgdG9rZW4iLCJleHAiOjE1OTgwNTczNzEsInVzZXJOYW1lIjoiSnVzdGluLld1IiwiaWF0IjoxNTM4MDU3MzcxfQ.5nT8CMLkQA-zq6MmemquJdNucGsTQEK7Yu87BLCZcbo";
		
		JwtUtil.verifyToken(token);  // The Claim 'iss' value doesn't match the required one.


	}

	

	
	

}
