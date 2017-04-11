package ca.justa.api2.test;

import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;

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

public class TestUserApi {
	private CookieStore httpCookieStore;
	public static String URL_ROOT = "http://localhost:8080/vcaps3";
	
	@Before
	public void setup()  {
		httpCookieStore = new BasicCookieStore();
	}	
	
	@Test
	public void testUserApi() throws HttpException, IOException{
		
		
		String url = URL_ROOT +"/api/v2/users/all.json";

		LoginService.loginAsUser(httpCookieStore, "robertp");
		
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
		
		final HttpGet request = new HttpGet(url);
		 
		HttpResponse response = client.execute(request);
	    assertEquals(response.getStatusLine().getStatusCode(), 200);

		System.out.println(response.getStatusLine().getStatusCode());
		
		String body = LoginService.getReturn(response);
		
		System.out.println(body);
		
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(body);
		
		List<String> userName = JsonPath.read(document, "$.[?(@.userId==2)].username");
		
		assertEquals(userName.size(), 1);
		assertEquals(userName.get(0), "lixin");
		
		System.out.println("userName= " + userName);
	}
	
	// it comes from 
	// https://github.com/jayway/JsonPath
	@Test
	public void testJson(){		
		
		String json ="{store: "
				+ "{    book: ["
				+ "        {            "
				+ "category: 'reference',            "
				+ "author: 'Nigel Rees',            "
				+ "title: 'Sayings of the Century',"
				+ "            price: 8.95"
				+ "        },"
				+ "        {"
				+ "            category: 'fiction',"
				+ "            author: 'J. R. R. Tolkien',"
				+ "            title: 'The Lord of the Rings',"
				+ "            isbn: '0-395-19395-8',"
				+ "            price: 22.99"
				+ "        }    ],"
				+ "    bicycle: {"
				+ "        color: 'red',"
				+ "        price: 19.95"
				+ "    }"
				+ "},"
				+ " expensive: 10"
				+ "}";
		
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
		
		String author0 = JsonPath.read(document, "$.store.book[0].author");
		List<String> category = JsonPath.read(document, "store.book[?(@.author=='Nigel Rees')].category");

		System.out.println("author0= " + author0);
		System.out.println("category= " + category);
		
		assertEquals(author0, "Nigel Rees");

		assertEquals(category.size(), 1);
		assertEquals(category.get(0), "reference");

	}
	
	@After
	public void tearDown() throws Exception {
		
		// logout
		LoginService.logout(URL_ROOT, httpCookieStore);
		

	}

}
