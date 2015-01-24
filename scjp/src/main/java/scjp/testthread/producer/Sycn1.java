// this example show producer and consumer pattehrn , comes from 
// http://blog.csdn.net/coutcin/archive/2006/09/12/1213834.aspx
package scjp.testthread.producer;

import java.util.Date;
import java.util.LinkedList;

public class Sycn1 {

    private LinkedList<String> myList = new LinkedList<String>();
    private int MAX = 10;

    public Sycn1() {
    }

    public void start() {
        new Producer().start();
        new Consumer().start();
    }

    public static void main(String[] args)  {
        Sycn1 s1 = new Sycn1();
        s1.start();
    }

    class Producer extends Thread {

        @Override
        public void run() {
            while (true) {
                synchronized (myList) {
                    try {
                        while (myList.size() == MAX) {
                            System.out.println("warning: it's full!");
                            myList.wait();
                        }
                        String o = "" + new Date();
                        if (myList.add(o)) {
                            System.out.println("Producer: " + o);
                            myList.notify();
                        }
                    
                    } catch (InterruptedException ie) {
                        System.exit(12);
                    }
                }
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    System.exit(12);
                }                
            }
        }
    }

    class Consumer extends Thread {

        @Override
        public void run() {
            while (true) {
                synchronized (myList) {
                    try {
                        while (myList.size() == 0) {
                            System.out.println("warning: it's empty!");
                            myList.wait();
                        }
                        String o = myList.removeLast();
                        System.out.println("Consumer: " + o);

                        myList.notify();
                    } catch (InterruptedException ie) {
                        System.exit(12);
                    }
                }
                
                try {
                    Thread.sleep(300);
                    // <1000 of producer, so it is hungry consumer
                    // so the application always complain "warning: it's empty!"
                    // if otherwise, it is work-hard producer, then "warning: it's full!"
                } catch (InterruptedException ie) {
                    System.exit(12);
                }


            }
        }
    }
}
