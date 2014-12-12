package com.justa.mock.bad.code;

/** 
 * this is bad code, we design it only for demo of jmockit
 */
public class JSFUtil {
	
	public JSFUtil(){
		throw new RuntimeException("can't call it.");
	}
	
	public static JSFUtil getCurrentInstance() {
		throw new RuntimeException("can't call it.");
	}
	
	public JSFUtil getInstance() {
		throw new RuntimeException("can't call it.");
	}
	
	public String getName() {
		throw new RuntimeException("can't call it.");
	}
	

}
