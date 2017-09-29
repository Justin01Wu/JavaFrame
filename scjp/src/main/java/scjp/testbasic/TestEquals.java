
package scjp.testbasic;

/**
Since equals() method is not defined in my class,
the equals() method of Object class, the parent
class will be used. The equals() method of Object
class returns true when two object references being
compared, point to the same memory area, which is same
as the output of obj1 == obj2. Since m1 and m2 are two
different object references, they point to different
memory areas and hence false will be returned by equals()
and thus "Both are not equal" will be printed.
*/
public class TestEquals {

    int x;

    TestEquals(int i) {
        x = i;
    }

    public static void main(String args[]) {
        TestEquals m1 = new TestEquals(100);
        TestEquals m2 = new TestEquals(100);

        if (m1.equals(m2)) {
            System.out.println("Both are equal");
        } else {
            System.out.println("Both are not equal");
        }
    }
}
