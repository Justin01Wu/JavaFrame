package com.justa.test.aws.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

public class MemcachedSingleNode {
	private static String ServerName = "localhost";
	private static int Port = 11211;
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		//initialize the SockIOPool that maintains the Memcached Server Connection Pool
		
		String serverName = System.getenv("CacheServerName");   
		if(serverName != null){
			ServerName = serverName ;			
		}
		String port = System.getenv("CachePort"); 		
		if(port != null){
			Port = Integer.parseInt(port) ;			
		}
		
		String serverUrl = ServerName+":"+ Port;
		System.out.println("serverUrl =" + serverUrl);
		
		MemcachedClient mcc = getSingleMemCachedClient(ServerName, Port);
		
		//add simple value into cache
		OperationFuture<Boolean> x= mcc.add("MyKey1", 5, "Original");  // expired in 5 seconds
		System.out.println("added MyKey1: "+ x.get());
		
		System.out.println("get MyKey1: "+mcc.get("MyKey1"));
		
		//add simple value into cache
		List<String> bigList = new ArrayList<>();
		for(int i=0;i<100;i++) {
			bigList.add("asksajhidizxc"+i);
		}
		x= mcc.add("MyKey2", 5, bigList);
		System.out.println("added MyKey2: "+ x.get());
		
		System.out.println("get MyKey2: "+mcc.get("MyKey2"));
		
		Thread.sleep(7000);
		
		System.out.println("get MyKey1: "+mcc.get("MyKey1")); // will print null because it is expired
		
		System.out.println("get MyKey2: "+mcc.get("MyKey2")); // will print null because it is expired
		
	}
	
	private static MemcachedClient getSingleMemCachedClient(String serverName, int port) throws IOException{
		
		System.out.println("connecting  memcached server: "+serverName + ":" + port);
		
		InetSocketAddress address =  new InetSocketAddress(serverName, port);
		MemcachedClient mcc = new MemcachedClient(address);
		System.out.println("Connection to server sucessfully");
		return mcc;
	}
}
