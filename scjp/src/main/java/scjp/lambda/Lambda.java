package scjp.lambda;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// from https://www.w3schools.com/java/java_lambda.asp
// please also see https://www.baeldung.com/java-8-lambda-expressions-tips

@FunctionalInterface // this will keep interface is functional interface, stop incorrect interface
interface StringFunction {
	String run(String str);
	default void defaultFunc() {};
	// int aaa();  // this line will generate a compile error because  @FunctionalInterface only allow one abstract method
}

public class Lambda {
	public static void main(String[] args) {
		
		StringFunction exclaim = (s) -> s + "!";  // define a lambda function based on interface, it is an simplified anonymous interface
		// please see https://medium.com/swlh/understanding-java-8s-consumer-supplier-predicate-and-function-c1889b9423d
		StringFunction ask = (s) -> s + "?";
		
		printFormatted("Hello", exclaim);
		printFormatted("Hello", ask);
		
		ArrayList<Integer> numbers = new ArrayList<Integer>();
	    numbers.add(7);
	    numbers.add(1);
	    Consumer<Integer> method = (n) -> { System.out.println(n); };  // built in interface for single parameter lambda function
	    numbers.forEach( method );
	    
	    // Java provide those built in functional interfaces: Consumer, Supplier, Predicate and Functions
	    Consumer<Integer> printNumber = (n) -> System.out.println("testing them together:" + n);  // we can make it simple by removing {}
	    Predicate<Integer> greatThan5 = (n) -> n > 5;
	    Supplier<Double> doubleSupplier1 = () -> Math.random();
	    Function<Integer, Integer> add6 = (n) -> n + 6;
	    
	    numbers.stream().filter(greatThan5).map(add6).forEach(printNumber);  // now we use them together
	     
	    
	}

	public static void printFormatted(String str, StringFunction format) {
		String result = format.run(str);
		System.out.println(result);
	}
}
