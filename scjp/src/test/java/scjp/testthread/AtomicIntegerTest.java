package scjp.testthread;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

public class AtomicIntegerTest {

	public static final AtomicLong  myUUID;
	
	static {
		Date date = new Date();
		myUUID= new AtomicLong(date.getTime());
	}
	
	@Test
	// this is a good simple example to use Atomic to get UUID  
	public void testUUID() throws InterruptedException {
		
		for(int i=0;i<10;i++) {
			System.out.println(myUUID.incrementAndGet());			
		}
		
	}
	
	
	
}
