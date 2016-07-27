package jdktest;

import java.io.Closeable;
import java.io.IOException;

class MyClosable implements Closeable {

	private String name;

	public MyClosable(String name) {
		this.name = name;
	}

	public MyClosable(boolean throwError) {
		if (throwError) {
			throw new RuntimeException("can't create MyClosable");
		}
	}

	public void close() throws IOException {
		System.out.println("MyClosable is closing " + name);
	}

}

public class ClosableTest {

	public static void main(String[] args) throws IOException {

		try (MyClosable me = new MyClosable("111")) { // this grammar start
														// since java 1.7
			System.out.println("do something111");
		} catch (Exception e) { // this line will automatically close me on
								// async way
			e.printStackTrace();
		}

		System.out.println("\n\r\n\r");

		try (MyClosable me1 = new MyClosable("abc");
				MyClosable me2 = new MyClosable("def")) { // this grammar start
															// since java 1.7
			System.out.println("do something222");
		} catch (Exception e) { // this line will call me2 firstly, then call me1 firstly
			e.printStackTrace();
		}

		System.out.println("\n\r\n\r");

		try (MyClosable me = new MyClosable(true)) { // this grammar start since java 1.7
			System.out.println("do something333"); // this line didn't run because new MyClosable(true) throw exception
		} catch (Exception e) { // this line will NOT close me because me is null
			e.printStackTrace();
		}

		System.out.println("\n\r\n\r");

		try (MyClosable me1 = new MyClosable("444");
				MyClosable me2 = new MyClosable(true);) { // this grammar start since java 1.7
			System.out.println("do something444"); // this line didn't run because new MyClosable(true) throw exception

		} catch (Exception e) { // this line will ONLY close 444 because me2 is null
			e.printStackTrace();
		}

	}
}
