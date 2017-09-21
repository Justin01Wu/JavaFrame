package scjp.loading;

// https://stackoverflow.com/questions/8106021/java-class-forname-vs-thread-currentthread-getcontextclassloader-loadclass
public class TestClassLoading {

	 
	 public static void main(String[] args) throws ClassNotFoundException {
		
		 Class test = Class.forName("scjp.loading.Class78");   //Outputs "Hello from Class78 static block"
		 
		//Thread.currentThread().getContextClassLoader().loadClass("scjp.loading.Class78");   // won't output anything
	 }
}
