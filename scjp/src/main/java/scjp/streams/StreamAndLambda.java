package scjp.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * comes from
 * https://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/ In
 * functional programming, a monad is a structure that represents computations
 * defined as sequences of steps. A type with a monad structure defines what it
 * means to chain operations, or nest functions of that type together.
 */
public class StreamAndLambda {

	public static void main(String[] args) {
		List<String> myList = Arrays.asList("a1", "a2", "b1", "c2", "c1");

		myList
	    	.stream()
	    	.filter(s -> s.startsWith("c"))   // lambda function is also called arrow function
	    	.map(String::toUpperCase)
	    	.sorted()
	    	.forEach(System.out::println);
		
		Arrays.stream(new int[] {1, 2, 3})
	    	.map(n -> 2 * n + 1)
	    	.average()
	    	.ifPresent(System.out::println);  // 5.0		
		
		Stream.of("d2", "a2", "b1", "b3", "c")
	    .filter(s -> {
	        System.out.println("filter: " + s);  // won't print because it has no terminal operation 
	        return true;
	    });

	}

}
