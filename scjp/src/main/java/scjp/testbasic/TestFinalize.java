/*
What will happen when you attempt to compile and run the following code?
 
 1) Compile time error
 2) Compilation and output of "finalizing" at runtime
 3) Compilation but no output at runtime
 4) Compilation but runtime exception
 answer is 2
 */
package scjp.testbasic;

class Archer {
    public void finalize(){
        System.out.println("finalizing");
    }
    
}
public class TestFinalize{

    public static void main(String argv[]){
        new TestFinalize();
    }
    
    public TestFinalize(){
        
        @SuppressWarnings("unused")
		Archer a = new Archer();
        
        a=null;
        System.gc();
        
        @SuppressWarnings("unused")
		int x=1;
        
        System.out.println("hi");
        
        System.out.println("end");
    }
}
