/* this will throw an IllegalMonitorStateException.
The wait/notify protocol can only be used within code that is
synchronized. In this case calling code does not have a lock on
the object(not synchronized) and will thus cause an Exception at runtime.
 */
package scjp.testthread;

public class TestWaitException {

    int i = 0;

    public static void main(String argv[]) {
        TestWaitException t = new TestWaitException();
        t.myMethod();
    }

    public void myMethod() {
        while (true) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
            i++;
        }
    }
}
