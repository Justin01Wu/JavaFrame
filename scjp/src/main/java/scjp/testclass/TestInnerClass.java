/*
What is the result:
A. Prints out "Sample"
B. Program produces no output but terminates correctly.
C. Program does not terminate.
D. The program will not compile
the Answer is A,  please notice that semicolon 
 */


package scjp.testclass;

public final class TestInnerClass {
    
    class Inner {
        void test() {
            
            if (TestInnerClass.this.flag);            
            {
                sample();
            }
        }
    }
    
    
    private boolean flag = false;
    
    public void sample() {
        System.out.println("Sample");
    }
    
    public TestInnerClass() {
        (new Inner()).test();
    }
    
    public static void main(String args[]) {
        new TestInnerClass();
    }
    
}
