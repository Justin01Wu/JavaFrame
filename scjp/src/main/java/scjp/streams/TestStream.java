package scjp.streams;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestStream {

	public static void main(String[] args) throws IOException, URISyntaxException {

		print10Integer();
		System.out.println();

		print5Integer();
		System.out.println();

		printSum();
		System.out.println();

		printFirstString();
		System.out.println();

		printFilteredString();
		System.out.println();
		
		printSquartAverge();
		System.out.println();
		
		handleStreamFromFile();
		System.out.println();
		
		reduceDoubleList();
		System.out.println();
	}

	private static void print10Integer() {
		IntStream
		.range(1, 10) // create a integer stream with range 1 to 10
		.forEach(System.out::print); // print them
		System.out.println();
	}

	private static void print5Integer() {
		IntStream
		.range(10, 20) // create a integer stream with range 10 to 20
		.skip(5) // skip first 5 item
		.forEach(x -> System.out.println(x));
	}

	private static void printSum() {

		System.out.println(
				IntStream.range(-1, 4) // create a integer stream with range -1 to 4
				.sum());
	}

	private static void printFirstString() {
		Stream.of("Yan", "Rita", "Justin") // create a string stream with given values 10 to 20
				.sorted()
				.findFirst()
				.ifPresent(System.out::println);
	}

	private static void printFilteredString() {

		String[] names = { "Yan", "Rita2", "Rita", "Justin" };
		Arrays.stream(names) // create a string stream from an array
				.filter(x -> x.startsWith("R"))
				.sorted()
				.forEach(System.out::println);
	}
	
	private static void printSquartAverge() {

		Arrays.stream( new int[] {2,3,4,5})  // create a int stream from an array
				.map(x -> x * x)
				.average()
				.ifPresent(x -> System.out.println("average = " + x));
	}
	
	private static void handleStreamFromFile() throws IOException, URISyntaxException {
		Stream<String> bands = Files.lines(Paths.get(ClassLoader.getSystemResource("bands.txt").toURI()));
		bands.sorted()
		.filter(x -> x.length()>13)
		.forEach(System.out::println);
		bands.close();

	}
	
	private static void reduceDoubleList() {
		double total = Stream.of(7.5,1.5, 4.8) // create a double stream with given values 
				.reduce(0.0, (Double a, Double b) -> a +b );
		System.out.println("total = " + total);
	}

}
