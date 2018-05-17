package scjp.thread.jdk8;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// it comes from https://www.callicoder.com/java-callable-and-future-tutorial/
public class FutureAndCallableExample {
	
	   public static void main(String[] args) throws InterruptedException, ExecutionException {
		   
	        

	        Callable<String> callable = new Callable<String>() {
	            @Override
	            public String call() throws Exception {
	                // Perform some computation
	                Thread.sleep(3000);
	                return "Return some result";
	            }
	        };
	        
	        System.out.println("Submitting Callable");
	        
	        ExecutorService executorService = Executors.newSingleThreadExecutor();	        
	        Future<String> future = executorService.submit(callable);

	        // This line executes immediately
	        System.out.println("Do something else while callable is getting executed");

	        System.out.println("Retrieve the result of the future");
	        // Future.get() blocks until the result is available
	        String result = future.get();
	        
	        
	        System.out.println(result);

	        executorService.shutdown();
	    }

}
