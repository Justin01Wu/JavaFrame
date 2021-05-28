package scjp.lambda;

import java.util.ArrayList;
import java.util.function.Consumer;

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
		
		StringFunction exclaim = (s) -> s + "!";  // define a lambda function based on interface
		StringFunction ask = (s) -> s + "?";
		
		printFormatted("Hello", exclaim);
		printFormatted("Hello", ask);
		
		ArrayList<Integer> numbers = new ArrayList<Integer>();
	    numbers.add(5);
	    numbers.add(9);
	    numbers.add(8);
	    numbers.add(1);
	    Consumer<Integer> method = (n) -> { System.out.println(n); };  // built in interface for single parameter lambda function
	    numbers.forEach( method );
	}

	public static void printFormatted(String str, StringFunction format) {
		String result = format.run(str);
		System.out.println(result);
	}
}
