package scjp.testexception;

// it comes from http://jawspeak.com/2010/05/26/hotspot-caused-exceptions-to-lose-their-stack-traces-in-production-and-the-fix/
//  wJVM ill remove stack traces in certain exceptions if they happen enough
// please use -XX:-OmitStackTraceInFastThrow  if don't want JVM do it. it will affect performance
public class NpeThief {
	 public void callManyNPEInLoop() {
	        for (int i = 0; i < 100000; i++) {
	            try {
	                ((Object)null).getClass();
	            } catch (Exception e) {
	                // This will switch from 2 to 0 (indicating our problem is happening)
	            	if(e.getStackTrace().length == 0){
	            		System.out.println("stack trace is removed after times throwing it " + i);	
	            		return;
	            	}
	                
	            }
	        }
	    }
	    public static void main(String ... args) {
	        NpeThief thief = new NpeThief();
	        thief.callManyNPEInLoop();
	    }
}
