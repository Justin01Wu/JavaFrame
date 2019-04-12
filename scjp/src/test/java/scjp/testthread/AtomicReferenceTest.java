package scjp.testthread;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

public class AtomicReferenceTest {

	@Test
	// from http://tutorials.jenkov.com/java-util-concurrent/atomicreference.html
	public void testSwitch() {
		String initialReference = "initial value referenced";

		AtomicReference<String> atomicStringReference =
		    new AtomicReference<String>(initialReference);

		String newReference = "new value referenced";
		boolean exchanged = atomicStringReference.compareAndSet(initialReference, newReference);
		System.out.println("exchanged: " + exchanged);

		exchanged = atomicStringReference.compareAndSet(initialReference, newReference);
		System.out.println("exchanged: " + exchanged);
	}
	
	@Test
	public void test2() throws InterruptedException {
		RunnableA objA= new RunnableA(123);
        Thread a = new Thread(objA);
		RunnableA objB= new RunnableA(-235);
        Thread b = new Thread(objB);    
          
        a.start();
        b.start();
        b.join();
        a.join();
        
        System.out.println(RunnableA.globalNumber.get());
        // we can't guarantee it is 123
	}

}
