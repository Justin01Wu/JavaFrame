/*
When will the string "Hi there" be printed?

A) Never.
B) Each time a new instance is created.
C) Once when the class is first loaded into the Java virtual machine.
D) Only when the static method is called explicitly.
--------++-----++---88------------++--
 The answer is C
 */
package scjp.testclass;

public class StaticInitialize {

    static {
        System.out.println("Hi there");
    }

    public void print() {
        System.out.println("Hello");
    }

    public static void main(String args[]) {
        StaticInitialize st1 = new StaticInitialize();
        st1.print();
        StaticInitialize st2 = new StaticInitialize();
        st2.print();
    }

}
