package com.justa.test.aws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

//can only access public permission files
public class GetObjectOnHttpWithApacheHttpClient {
	public static void main(String[] args) throws ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException, HttpException {
		String url = "https://justa.s3.amazonaws.com/UploadObjectFromString.txt";
		HttpGet request = new HttpGet(url);

		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);
		String result = getReturn(response);		
		System.out.println(result);
		
		
	}
	
	private static String getReturn(HttpResponse response) throws HttpException, IOException {
		if (response.getEntity() == null) {
			return "";
		}

		StringBuffer result = new StringBuffer();

		try (BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		}

		return result.toString();

	}

}
