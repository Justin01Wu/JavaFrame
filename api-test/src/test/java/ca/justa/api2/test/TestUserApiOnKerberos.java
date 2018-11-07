package ca.justa.api2.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

public class TestUserApiOnKerberos {
	
	private CookieStore httpCookieStore;
	public static String URL_ROOT = "http://cavcdbdev02:18080/vcaps3";
	
	@Before
	public void setup()  {

		
		URL location = LoginKerberosService.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(location.getFile());
        // JAAS config file path, sample: /C:/samples/javaFrame/javaFrame/JavaFrame/api-test/target/classes/
        String loginConfPath = location.getFile().toString() +"login.conf";
        
		System.setProperty("sun.security.krb5.debug", "true");		
		
        System.setProperty("java.security.auth.login.config", loginConfPath);
        System.setProperty("java.security.krb5.conf", location.getFile().toString() +"krb5.conf");
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");

		httpCookieStore = new BasicCookieStore();
	}	
	
	@Test
	public void testUserApi() throws HttpException, IOException, LoginException{
		
        String user = "justin.wu";
        
        String loginUrl = URL_ROOT + "/SecurityServlet";
        String url = URL_ROOT +"/api/v2/users/all.json";
        

		LoginKerberosService.login(user, passwordBase64, loginUrl, httpCookieStore);
		
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		final HttpGet request = new HttpGet(url);
		 
		HttpResponse response = client.execute(request);
	    assertEquals(response.getStatusLine().getStatusCode(), 200);

		System.out.println(response.getStatusLine().getStatusCode());
		
		String body = LoginService.getReturn(response);
		
		//System.out.println(body);
		
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(body);
		
		List<String> userName = JsonPath.read(document, "$.[?(@.userId==2)].username");
		
		assertEquals(userName.size(), 1);
		assertEquals(userName.get(0), "lixin");
		
		System.out.println("userName= " + userName);
	}

	
	@After
	public void tearDown() throws Exception {
		
		// logout
		LoginService.logout(URL_ROOT, httpCookieStore);
		

	}
	
	
	
	
	private static String passwordBase64 = "my windows domain password base64 encode";

}
