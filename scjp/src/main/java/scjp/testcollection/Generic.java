package scjp.testcollection;

import java.util.ArrayList;
import java.util.List;

public class Generic {
	
	
	public static void main(String[] args) {
		List x = new ArrayList();
		List<?> y = new ArrayList<Integer>();
		x= y;
		y=x;
		
		List<Object> z = new ArrayList<Object>();
		//z= y;  compile error
		z= x;  // no compile error 
		
		List<? extends RuntimeException> aa = new ArrayList< RuntimeException>();
		//  aa.add( new IllegalArgumentException());  // compile error , why?
		
	}

}
