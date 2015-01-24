

//   The Output is:  a =4
//  Because all static initializers code is executed at the class load time,
// even before main() is called the value of static variable is initialized

package scjp.testclass;

class InitOrder {
    static int a = 7;
    static { a += 5; }

    public static void main(String args[]) {
        System.out.println("a = " + a);
    }

    static { a /= 3; }
}
