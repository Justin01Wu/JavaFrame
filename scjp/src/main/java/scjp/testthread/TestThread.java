package scjp.testthread;

public class TestThread {
    public static void main(String args[]) {
        Xyz r = new Xyz();
        //Xyz p = new Xyz();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        //Thread w1 = new Thread(p);
        //Thread w2 = new Thread(p);

        t1.setName("Thread_t1");
        t2.setName("Thread_t2");
        //w1.setName("Thread_w1");
        //w2.setName("Thread_w2");

        t1.start();
        t2.start();
        //w1.start();
        //w2.start();
    }
}


class Xyz implements Runnable {
    private int i;
    private int j;

    public void run() {
//synchronized(this){
        while (true) {

            if (i == 1000) {
                break;
            }
//                System.out.println("i= " + i++ +"," + "j=" + j++);
//                System.out.println(Thread.currentThread().getName());

            i++;
            j++;
            if(i!=j){
                System.out.println("i= " + i +"," + "j=" + j);
                System.out.println(Thread.currentThread().getName());
            }
        }
        //}
    }
}
