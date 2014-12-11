package com.justa.mock.bad.code;

public class NoWayToCallInUnitTest {
	
	public static void call(){
		throw new RuntimeException("you can't call me");
	}

}
