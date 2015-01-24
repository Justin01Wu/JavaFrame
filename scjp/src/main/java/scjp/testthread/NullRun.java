package scjp.testthread;

public class NullRun extends Thread
{
        public static void main(String args[])
        {
                new NullRun().start();
        }

        // public void run() throws Exception {} this is an error
        // becaues override can't extend exception
}
