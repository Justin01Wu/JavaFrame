/*
 Which two statements are true? (Choose Two)
 A. The program prints "ABBCAD"
 B. The program prints "CDDACB"
 C. The program prints "ADCBADBC"
 D. The output is a non-deterministic point because of a possible deadlock condition
 E. The output is dependent on the threading model of the system the program is running on.
 the answer is D,E
 */
package scjp.testthread;

public class DeadLock {
    public static void main(String[] args) {
        final StringBuffer s1 = new StringBuffer();
        final StringBuffer s2 = new StringBuffer();

        new Thread() {
            public void run() {
                synchronized (s1) {
                    s1.append("A");
                    synchronized (s2) {
                        s2.append("B");
                        System.out.print(s1);
                        System.out.print(s2);
                    }
                }
            }
        }.start();

        new Thread() {
            public void run() {
                synchronized (s2) {
                    s2.append("C");
                    synchronized (s1) {
                        s1.append("D");
                        System.out.print(s2);
                        System.out.print(s1);
                    }
                }
            }
        }.start();

    }
}
