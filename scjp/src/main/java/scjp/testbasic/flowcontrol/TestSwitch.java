//What will happen when you attempt to compile and run the following code?

// the answer is :
//  ten
//  twenty
//   --------------
//  This is the default output

//  the "break" is significant
package scjp.testbasic.flowcontrol;

public class TestSwitch {

    public static void main(String argv[]) {
        TestSwitch mc = new TestSwitch();
        mc.myMethod(10);
        System.out.println("------------------------------");
        mc.myMethod(7);
    }

    public void myMethod(int k) {
        switch (k) {
            default:
                System.out.println("This is the default output");
                break;
            case 10:
                System.out.println("ten");   // missed a break here
            case 20:
                System.out.println("twenty");
                break;
        }
    }
}
