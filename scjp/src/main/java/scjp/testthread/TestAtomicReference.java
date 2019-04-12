package scjp.testthread;

import java.util.concurrent.atomic.AtomicReference;

/*
 * from  https://stackoverflow.com/questions/18428558/correct-understanding-of-concepts-like-volatile-syncronized-and-atomicreference
 * 
 * Underneath, AtomicReference uses a volatile member, 
 * ensuring that the get() and set() and similar operations are accessing non-thread-specific memory. 
 * This is how it attains thread safety without synchronization. 
 * As Marko and ScottB both stated, 
 * there are really only two ways to guarantee that a particular thread is seeing the "real" (non-thread-specific) value 
 * of a particular variable: that variable is declared as "volatile" 
 * which means it should not be cached in thread-specific memory, 
 * or that variable is guarded by a synchronized block (all thread-specific memory is updated at synchronize).
 */
public class TestAtomicReference {

	public static void main(String[] args) throws InterruptedException {
		
		RunnableA objA= new RunnableA(123);
        Thread a = new Thread(objA);
		RunnableA objB= new RunnableA(-235);
        Thread b = new Thread(objB);        
        a.start();  
        b.start();
        b.join();
        a.join();
        System.out.println(RunnableA.globalNumber.get());

	}	

}

class RunnableA implements Runnable {
	
	public static final AtomicReference<Integer> globalNumber = new AtomicReference<>();
	// thread safe even it is public static 
	
	private int value;
	public RunnableA(int value){
		this.value = value;
		
	}
    public void run(){
        try {
			Thread.sleep(1000l);  // sleep 1 second
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  

        globalNumber.compareAndSet(null, value);
        // this can be used to initialize a variable from configure file
    }
}
