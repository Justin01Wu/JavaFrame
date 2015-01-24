// show how a permanent wait happens
// if x >> y then permanent wait
package scjp.testthread;



public class PermanentWait extends Thread {
    public static void main(String[] args) {
        RunnableObj obj=new RunnableObj();
        Thread b = new Thread(obj);

        obj.setControlThread(currentThread());

        b.start();  // inside this method obj is locked by Thread b.
        long x=2000L;
        try{
            //b.join(); // This a standard way to wait
            Thread.sleep(x);
        }catch(Exception e){e.printStackTrace();}

        synchronized (obj) {  // synchronized on b or obj is OK
            // But wait must is the same object
            try {
                System.out.println("Waiting for b to complete...");
                obj.wait();
                // if x>>y , this will be a permanent wait. 
                // because none notify it to wake up.
            } catch (InterruptedException e) {}
        }

        System.out.println("Total is:" + obj.total);
    }
}
class RunnableObj implements Runnable {
    int total;
    private Thread controlThread;

    void setControlThread(Thread t){
        controlThread=t;
    }

    public void run(){
       long y=1000L;
        try{
            Thread.sleep(y);
        }catch(Exception e){e.printStackTrace();}


        synchronized(this){
            for (int i = 0; i < 100; i++) {
                total += i;
            }
            notify();

            //controlThread.interrupt();
            // This another way to fire.
        }
    }
}
