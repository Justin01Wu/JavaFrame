package scjp.testthread.producer;

public class Producer implements Runnable {
    private MailServer mailServer;
    
    public Producer(MailServer mailServer) {
        this.mailServer = mailServer;
    }

    public void run() {
        int i = 0;
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(12);
            }
            i++;
            mailServer.storeMessage("msg_" + i);
        }

    }
}
