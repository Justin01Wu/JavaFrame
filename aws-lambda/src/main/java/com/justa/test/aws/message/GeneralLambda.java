package com.justa.test.aws.message;

import java.io.File;
import java.text.NumberFormat;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GeneralLambda implements RequestHandler<Map<String, Object>,String> {
	
	@Override
	public String handleRequest(Map<String, Object> input, Context context) {
		
		if(input != null) {
			System.out.println("==>input type: " + input.getClass().getName());
			System.out.println("==>input value: " + input);
		}
		
		getSysteInfo();
		return null;
	}
	
	static void getSysteInfo() {
		
		NumberFormat f = NumberFormat.getInstance();		
		
		System.out.println("==>Java version = " +  System.getProperty("java.version"));
		System.out.println("==>java.vm.version = " +  System.getProperty("java.vm.version"));		
		System.out.println("==>java.vendor = " +  System.getProperty("java.vendor"));
		System.out.println("==>java.vm.vendor = " +  System.getProperty("java.vm.vendor"));
		System.out.println("==>java.vm.name = " +  System.getProperty("java.vm.name"));			 
		System.out.println("==>Java home = " +  System.getProperty("java.home"));
		
		System.out.println("==>Os name = " +  System.getProperty("os.name"));
		System.out.println("==>Os version = " +  System.getProperty("os.version"));
		System.out.println("==>user.dir = " +  System.getProperty("user.dir"));
		System.out.println("==>user.name = " +  System.getProperty("user.name"));
		System.out.println("==>user.home = " +  System.getProperty("user.home"));
		
		long df = new File("/").getFreeSpace();
		long dt = new File("/").getTotalSpace();
		System.out.println("==>free disk = " + f.format(df / 1024));
		System.out.println("==>total disk = " + f.format(dt / 1024) );

		Runtime runtime = Runtime.getRuntime();
		long maxMemory = runtime.maxMemory();
		long allocatedMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();
		System.out.println("==>free memory: " + f.format(freeMemory / 1024) );
		System.out.println("==>allocated memory: " + f.format(allocatedMemory / 1024) );
		System.out.println("==>max memory: " + f.format(maxMemory / 1024) );
		System.out.println("==>total free memory: " + f.format((freeMemory + (maxMemory - allocatedMemory))));
		
		
	}

}