/*
 What will happen when you attempt to compile and run the following code?
 1) Compile time error
 2) Compilation and output of 99
 3) Compilation and output of 100
 4) Compilation, but runtime error because an instance of Trebble cannot be cast to type Base
 The answer is 3
 */
package scjp.testclass.inherit;

class Trebble{
    int i = 99;
}
class Base3 extends Trebble{
    int i =100;
}
public class Central extends Base3{
    public static void main(String argv[]){
        Central c = new Central();
        c.wynyard();
    }
    public void wynyard(){
        Trebble t = new Central();
        System.out.println(t.i);  // output 99
        Base3 b = (Base3) t;
        System.out.println(b.i);  // output 100
    }

}

