package com.justa.test.lambda.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/** demo how to call a RESTful APi*/
public class CallApi {

	public static void main(String[] args) throws IOException {
		
		initHttpsWithoutValidation();  // skip invalid ssl certificate validation 
		
		String apiUrl = "https://bod76cqgu4.execute-api.us-east-1.amazonaws.com/default/returnMyName";
		URL url = new URL(apiUrl);
		System.out.println("call api : "+ apiUrl);
		
		// setup connection
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		
		//conn.setDoOutput(true);  
		// only for post method, it will fail on 403 error if it is a GET
		
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");		
		//conn.setRequestProperty("Authorization", myJWTToken);	

		// add payload
		//OutputStream os = conn.getOutputStream();
		//os.write(payload);  
		//os.flush();
		
		ObjectMapper mapper = new ObjectMapper();
		
		String result = getResponse(conn, mapper);
		System.out.println(result);

	}
	
	public static String getResponse(HttpsURLConnection conn, ObjectMapper mapper ) throws IOException {
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			int httpStatus = conn.getResponseCode();
			InputStream is = conn.getErrorStream();
				
			String result = getStream(is);
			String contentType =  conn.getContentType();
			if(contentType.equalsIgnoreCase("application/problem+json")){
				JsonNode jsonNode = validJsonStr(result);
				String detail = jsonNode.get("detail").asText();
				System.err.println( detail );
				conn.disconnect();
				throw new IOException("http status: "+ httpStatus + ", rule engine error: " + detail);
			}else {					
				System.err.println("Error Code: "  + conn.getResponseCode() );
				System.err.println(result);
			}
				
			conn.disconnect();
			throw new IOException("Failed : HTTP error code: " + httpStatus);
		}	
		InputStream is = conn.getInputStream();
		
		String result = getStream(is);		

		prettyPrint(result, mapper);
		return result;
	}
	
	private static String getStream(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String output;
		StringBuffer response = new StringBuffer();
		while ((output = br.readLine()) != null) {
			response.append(output);
		}
		
		String result = response.toString();	
		return result;
	}
	
	public static JsonNode validJsonStr(final String jsonStr) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			@SuppressWarnings("unused")
			JsonNode jsonNode = objectMapper.readTree(jsonStr);
			return jsonNode;
		} catch (IOException e) {
			throw new RuntimeException("invalid json string");
		}
	}
	
	private static String prettyPrint(String s, ObjectMapper mapper) throws IOException {
		
		String result = s;

		Object json = mapper.readValue(result, Object.class);
		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		System.out.println(indented);
		return result;
	}
	
	public static void initHttpsWithoutValidation() {
		
		TrustManager[] trustAllCerts = new TrustManager[] { 
			    new X509TrustManager() {     
			        public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
			            return new X509Certificate[0];
			        } 
			        public void checkClientTrusted( 
			            java.security.cert.X509Certificate[] certs, String authType) {
			            } 
			        public void checkServerTrusted( 
			            java.security.cert.X509Certificate[] certs, String authType) {
			        }
			    } 
			}; 
		try {
		    SSLContext sc = SSLContext.getInstance("SSL"); 
		    sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		    
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} 
	}

}
