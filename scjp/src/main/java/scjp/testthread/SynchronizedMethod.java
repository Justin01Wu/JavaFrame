/*
 What happens when this code compiles and run?
 a) will print x ,y in order 11 22
 b) will print x ,y twice in order 11 22 11 22
 c) will print x ,y in order 12 12
 d) will print x ,y order is unpredictable.
 e) Compilation error.
 f) Runtime Exception.

 the answer is a
 */
package scjp.testthread;

class SynchronizedMethod implements Runnable {
    private int x;
    private int y;
    public synchronized void run() {
        x++;
        y++;
        System.out.println("x="+x + "; y=" + y);
    }

    public static void main(String args[]) {
        SynchronizedMethod that = new SynchronizedMethod();
        (new Thread(that)).start();
        (new Thread(that)).start();
    }
}
