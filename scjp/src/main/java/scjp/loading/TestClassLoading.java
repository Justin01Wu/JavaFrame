package scjp.loading;

// https://stackoverflow.com/questions/8106021/java-class-forname-vs-thread-currentthread-getcontextclassloader-loadclass
public class TestClassLoading {
	 
	 public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		 Class<?> test = Class.forName("scjp.loading.Class77");   //Outputs "Hello from Class77 static block"
		 Object a = test.newInstance();		 
		 System.out.println(a);
		 
		 Class<?> test2 = Thread.currentThread().getContextClassLoader().loadClass("scjp.loading.Class78");   // won't output anything
		 Object b = test2.newInstance();  // will run static block and constructor
		 System.out.println(b);
		 
		 Class<?> test3 = ClassLoader.getSystemClassLoader().loadClass("scjp.loading.Class79");   // won't output anything
		 // this method is unsafe, because many container will use multiple classLoader, like tomcat
		 // details: https://stackoverflow.com/questions/8100376/class-forname-vs-classloader-loadclass-which-to-use-for-dynamic-loading
		 Object c = test3.newInstance();  // will run static block and constructor
		 System.out.println(c);

	 }
}
