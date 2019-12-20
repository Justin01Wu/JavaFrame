package com.justa.test.aws.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class MemcachedLambda implements RequestHandler<Object,String> {
	

	@Override
	public String handleRequest(Object input, Context context) {	
		String serverName = System.getenv("CacheServerName");   
		String port = System.getenv("CachePort"); 
		
		String serverUrl = "localhost:11211";
		if(serverName != null){
			serverUrl = serverName + ":" + port;			
		}
		
		System.out.println("serverUrl =" + serverUrl);
		
		GeneralLambda.getSysteInfo();
		
		MemCachedClient mcc;
		try {
			mcc = getMemCachedClient(serverUrl);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		//add some value in cache
		System.out.println("add status: "+mcc.add("1", "Original"));
		//Get value from cache
		System.out.println("Get from Cache: "+mcc.get("1"));

		System.out.println("add status: "+mcc.add("1", "Modified"));
		System.out.println("Get from Cache: "+mcc.get("1"));

		//use set function to add/update value, use replace to update and not add
		System.out.println("set status: "+mcc.set("1","Modified"));
		System.out.println("Get from Cache after set: "+mcc.get("1"));

		//use delete function to delete key from cache
		System.out.println("remove status: "+mcc.delete("1"));
		System.out.println("Get from Cache after delete: "+mcc.get("1"));

		multiQuery(mcc);		
		testBigObject(mcc);
		
		System.out.println(mcc.stats());
		return null;
	}
	
	private static void testBigObject(MemCachedClient mcc)  {
		List<String> bigList = new ArrayList<>();
		for(int i=0;i<100;i++) {
			bigList.add("asksajhidizxc"+i);
		}
		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, 10);  // expired in 10 seconds
		System.out.println("add MyKey2: "+mcc.add("MyKey2", bigList, now.getTime()));
		
		System.out.println("get MyKey2: "+mcc.get("MyKey2"));
			
	}
	
	private static void multiQuery(MemCachedClient mcc) {
		//Use getMulti function to retrieve multiple keys values in one function
		// Its helpful in reducing network calls to 1
		mcc.set("2", "2");
		mcc.set("3", "3");
		mcc.set("4", "4");
		mcc.set("5", "5");
		String [] keys = {"1", "2","3","INVALID","5"};
		HashMap<String,Object> hm = (HashMap<String, Object>) mcc.getMulti(keys);

		for(String key : hm.keySet()){
			System.out.println("KEY: "+key+" VALUE: "+hm.get(key));
		}
	}
	
	private static MemCachedClient getMemCachedClient(String serverUrl) throws IOException{
		
		System.out.println("connecting  memcached server: "+serverUrl);
		String[] servers = {serverUrl};
		SockIOPool pool = SockIOPool.getInstance("Test1");
		pool.setServers( servers );
		pool.setFailover( true );
		pool.setInitConn( 10 );
		pool.setMinConn( 5 );
		pool.setMaxConn( 250 );
		pool.setMaintSleep( 30 );
		pool.setNagle( false );
		pool.setSocketTO( 3000 );
		pool.setAliveCheck( true );
		pool.initialize();
		//Get the Memcached Client from SockIOPool named Test1
		MemCachedClient mcc = new MemCachedClient("Test1");
		
		Map<String, Map<String, String>> x = mcc.stats();
		if(x.isEmpty()) {
			throw new IOException("can't connect the server");
			// because servers is an array, so it won't get any exception in the whole code if no server is available!!
			//So we have to manually detect it.
		}
		return mcc;
	}

}
