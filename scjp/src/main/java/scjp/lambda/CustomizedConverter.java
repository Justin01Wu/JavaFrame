package scjp.lambda;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// test customized converter
public class CustomizedConverter {
	
	public static void main(String[] args) throws MalformedURLException {
		
		List<URL> urls = getUrlList();
		
		List<Integer> ports = urls.stream()
				.map(u -> u.getPort())
				.collect(Collectors.toList());
		
		System.out.println(ports);  // output: [8080, 443, -1]
		
		
		// https://stackoverflow.com/questions/67607802/how-to-convert-a-list-of-object-to-a-list-of-integer-in-java
		List<List<Object>> valuesModel = Arrays.asList(Arrays.asList(1,2,3,4), Arrays.asList(5,6,7,8));

		List<Integer> integers = valuesModel.stream()  		// Stream the list of lists to a stream of lists.
		           .flatMap(Collection::stream)     		// Then flatten those lists into a stream of object.
		            .map(ob->(Integer)ob)             		// cast the object to an Integer.
		             .collect(Collectors.toList()); 		// and collect into a List

		System.out.println(integers); // output: [1, 2, 3, 4, 5, 6, 7, 8]
		
	}
	
	private static List<URL> getUrlList() throws MalformedURLException{
		List<URL> urls = new ArrayList<>();
		
		URL url = new URL("http://localhost:8080/index.html");
		urls.add(url);
		url = new URL("https://www.google.com:443");
		urls.add(url);
		url = new URL("http://google.com");
		urls.add(url);
		return urls;
	}


}
