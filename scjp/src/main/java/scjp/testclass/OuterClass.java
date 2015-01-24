/*
You need to insert an inner class declaration at line 3. Which two inner class declarations are
valid?(Choose two.)
A. class InnerOne{
public static double methoda() {return d1;}
}
B. public class InnerOne{
static double methoda() {return d1;}
}
C. private class InnerOne{
double methoda() {return d1;}
}
D. static class InnerOne{
protected double methoda() {return d1;}
}
E. abstract class InnerOne{
public abstract double methoda();
} 
The answer is C and E
 * 
 *  A B error: no-static variable can't be referenced from a static method
 *   D is static class, can't use no-static variable.
   By the way, "static" can only be applied ti inner class.
 */
package scjp.testclass;

public class OuterClass {

    private double d1 = 1.0;

//insert code here

    
    
} 
