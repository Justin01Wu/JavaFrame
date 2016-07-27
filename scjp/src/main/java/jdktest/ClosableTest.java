package jdktest;

import java.io.Closeable;
import java.io.IOException;


class MyClosable  implements Closeable {
	
	public MyClosable(){}
	public MyClosable(boolean throwError){
		if(throwError){
			throw new RuntimeException("can't create MyClosable");
		}
	}

	public void close() throws IOException {
		System.out.println("MyClosable is closing");		
	}
	
}

public class ClosableTest {
	
	public static void main(String[] args) throws IOException{
		
		try(MyClosable me = new MyClosable()){  // this grammar start since java 1.7
			System.out.println("do something");
		}		// this line will automatically close me on async way
		

		try(MyClosable me = new MyClosable(true)){  // this grammar start since java 1.7
			System.out.println("do something222");  // this line didn't run because new MyClosable(true) throw exception 
		}		// this line will NOT close me because me is null

	}
}
