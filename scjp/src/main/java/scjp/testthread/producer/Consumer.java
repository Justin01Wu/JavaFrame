package scjp.testthread.producer;

public class Consumer implements Runnable {
    private MailServer mailServer;
    
    public Consumer(MailServer mailServer) {
        this.mailServer = mailServer;
    }


    public void run() {
        //System.out.println("starting Consumer...");
        while (true) {

            try {
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(12);
            }
            mailServer.retrieveMessage();
            //System.out.println("end Consumer...");

        }
    }
}
