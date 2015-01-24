package scjp.testthread;

class Moniter {
    private static final int second=13;

    private Integer x = 12;

    public void test() {
        try {
            synchronized (x) {
                System.out.println(x);
                if (x != second) {
                    Thread.sleep(2000);
                    x++;
                    //throw new InterruptedException("dddd");
                    throw new RuntimeException("dddd");
                    // any Exception will release lock of moniter
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

public class ExceptionInSynchronization extends Thread {

    Moniter moniter;

    ExceptionInSynchronization(Moniter moniter) {
        this.moniter = moniter;
    }

    public void run() {

        moniter.test();


    }

    public static void main(String args[]) {
        Moniter x = new Moniter();
        ExceptionInSynchronization t1 = new ExceptionInSynchronization(x);
        t1.start();
        ExceptionInSynchronization t2 = new ExceptionInSynchronization(x);
        t2.start();
    }
}
