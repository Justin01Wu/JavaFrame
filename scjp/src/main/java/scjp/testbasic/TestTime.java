package scjp.testbasic;

import java.util.Date;

public class TestTime {
	
	public static void main(String[] args) {
		int now = ((int) (System.currentTimeMillis() / 1000));
		System.out.println(now);
		
		Date fixed = new Date();
		fixed.setTime(1618844788000l);   // Mon Apr 19 11:06:28 EDT 2021
		System.out.println(fixed);     
	}

}
