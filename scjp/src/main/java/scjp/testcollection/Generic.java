package scjp.testcollection;

import java.util.ArrayList;
import java.util.List;

public class Generic {
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		
		
		@SuppressWarnings("rawtypes")
		List x = new ArrayList();
		List<?> y = new ArrayList<Integer>();
		x= y;
		y=x;
		
		@SuppressWarnings("unused")
		List<Object> z = new ArrayList<Object>();
		//z= y;  compile error
		z= x;  // no compile error 
		
		@SuppressWarnings("unused")
		List<? extends RuntimeException> aa = new ArrayList<>();
		// aa.add( new RuntimeException());  // compile error , why?
		//  aa.add( new IllegalArgumentException());  // compile error , why?
		
		List<RuntimeException> bb = new ArrayList<>();
		bb.add( new RuntimeException());
		bb.add( new IllegalArgumentException());
		
		List<RuntimeException> cc  = getList();
		cc.add( new RuntimeException());
		cc.add( new IllegalArgumentException());		

		List<IllegalArgumentException> dd  = getList3(IllegalArgumentException.class);  // this is better way
		dd.add( new IllegalArgumentException());		
		
		//List<RuntimeException> ee  = getList3(IllegalArgumentException.class);  // compile error: RuntimeException didn't match IllegalArgumentException
		
		//List<InstantiationException> ff  = getList3(InstantiationException.class);  // compile error: InstantiationException is not the child class of RuntimeException
		
	}
	
	public static <T extends RuntimeException> List<T> getList(){
		List<T> aa = new ArrayList<>();
		//aa.add( new RuntimeException());  // compile error , why?
		return aa;
	}
	
	public static List<RuntimeException> getList2(){
		List<RuntimeException> aa = new ArrayList<>();
		aa.add( new RuntimeException());  
		return aa;
	}

	//  this is better way comparing with getList2 and getList
	public static <T extends RuntimeException> List<T> getList3(Class<T> type) throws InstantiationException, IllegalAccessException{
		List<T> aa = new ArrayList<>();
		T a = type.newInstance();   
		aa.add( a);  
		return aa;
	}

	


}
