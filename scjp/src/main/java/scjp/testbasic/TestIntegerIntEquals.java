package scjp.testbasic;

public class TestIntegerIntEquals {

    public static void main(String args[]) {
        Integer i1 = new Integer(100);
        Integer i3 = new Integer(100);
        int i2 = 100;

        if (i1.equals(i2)) {
            System.out.println("Both are equal");  // get this
        } else {
            System.out.println("Both are not equal");
        }
        
        if (i1 == i2) {
            System.out.println("Both are ==");   // get this? why?
        } else {
            System.out.println("Both are not ==");
        }
        
        if (i2 == i1) {
            System.out.println("Both are ==");  // get this? why?
        } else {
            System.out.println("Both are not ==");
        }

        if (i3.equals(i1)) {
            System.out.println("Both are equals");  // get this? why?
        } else {
            System.out.println("Both are not equals");
        }
        
        String s1 = new String("100");
        String s2 = new String("100");
        
        if (s2 == s1) {
            System.out.println("Both are ==");
        } else {
            System.out.println("Both are not ==");   // get this.
        }
    }
}
