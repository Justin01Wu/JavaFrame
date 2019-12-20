package com.justa.test.aws.cache;

import java.util.Date;
import java.util.HashMap;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

// https://www.journaldev.com/24/memcached-java-client-example
public class MemcachedSample {
	
	private static String serverUrl = "localhost:11211";
	
	// you need to install Memcached by following the below url:
	// https://www.ubergizmo.com/how-to/install-memcached-windows/
	// it is pretty simple, 
	// but you can't directly start it, must use admiistrator to run those command:	
	//  # set it as service:
	//     memcached.exe -d install
	//  # start it
	//     start.bat
	// to make it simple, you can also start it from task manager service panel


	/**
	 * MemcachedJavaClient program to show the usage of different functions
	 * that can be performed on Memcached server with Java Client
	 * @param args
	 */
	public static void main(String[] args) {
		//initialize the SockIOPool that maintains the Memcached Server Connection Pool
		
		String serverName = System.getenv("CacheServerName");   
		String port = System.getenv("CachePort"); 		
		
		if(serverName != null){
			serverUrl = serverName + ":" + port;			
		}
		
		System.out.println("serverUrl =" + serverUrl);
		
		MemCachedClient mcc = getMemCachedClient(serverUrl);
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
		
		// save a java object
		Date date = new Date();  // must implement Serializable interface, will serialize it
		System.out.println("set date status: "+mcc.set("currentDate", date));   
		System.out.println("get date status: "+mcc.get("currentDate"));

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
		
		System.out.println(mcc.stats());
	}
	
	private static MemCachedClient getMemCachedClient(String serverUrl){
		
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
		return mcc;
	}
}
