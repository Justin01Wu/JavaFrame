package scjp.generic;

import java.util.ArrayList;
import java.util.List;

public class Wildcards {
	
	// Upper Bounded Wildcards
	// http://docs.oracle.com/javase/tutorial/java/generics/upperBounded.html
	public static void process(List<? extends MyStudent> list) {
		for (MyStudent n : list){
			System.out.println(n.getId());
		}
	}
	
	// Unbounded Wildcards
	// http://docs.oracle.com/javase/tutorial/java/generics/unboundedWildcards.html
	public static void printList(List<Object> list) {
	    for (Object elem : list){
	        System.out.println(elem + " ");
	    }
	    System.out.println();
	}
	
	//Lower Bounded Wildcards
	// http://docs.oracle.com/javase/tutorial/java/generics/lowerBounded.html

	public static void print2(List<? super MyStudent2> list) {
		for (Object elem : list){
	    	System.out.println(elem + " ");  
	    	//Lower Bounded Wildcards , rare case, because we have no way to find some userful methods
	    }
	}

	// subtyping
	// http://docs.oracle.com/javase/tutorial/java/generics/subtyping.html
	public static void subtyping() {
		
		List<Integer> lb = new ArrayList<>();
		//List<Number> la = lb;   // compile-time error
		
		List<? extends Integer> intList = new ArrayList<>();
		List<? extends Number>  numList = intList;  
		// OK. List<? extends Integer> is a subtype of List<? extends Number>
	}

	
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		MyDao myDao = new MyDao();
		
		List<MyStudent> student2s = myDao.findAllStudentsBySchoolId(12, MyStudent.class);	
		print2(student2s);  	//Lower Bounded Wildcards , rare case

	}
}
