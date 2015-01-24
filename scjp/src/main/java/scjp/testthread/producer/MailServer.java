// producer consumer pattern

package scjp.testthread.producer;


public class MailServer {

    
    private boolean request;
    private String message;

    public synchronized void storeMessage(String message) {
        while (request == true) {

            // No room for another message
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(12);

            }
        }
        request = true;
        System.out.println("store: "+message);
        this.message = message;
        notifyAll();
    }

    public synchronized String retrieveMessage() {
        while (request == false) {
            // No message to retrieve
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(12);
            }
        }
        request = false;
        notifyAll();
        System.out.println("retrieve: "+message);
        return message;
    }

    public static void main(String argv[]) {
        MailServer mailServer = new MailServer();

        Producer producer = new Producer(mailServer);
        Consumer consumer = new Consumer(mailServer);

        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(consumer);

        t1.start();
        t2.start();


    }
}
