/*
 What will happen if you attempt to compile and run the following code?
 1) Compilation but no output as the run method of slave is not correct
 2) Compilation and output of 100 several times followed by 200 several times
 3) Compilation and repeated output of 100
 4) Compile time error, while cannot be given an unconditional boolean value

 The answer is 3
*/
package scjp.testthread;

public class Master{
    boolean bContinue=false;
    public static void main(String argv[]){
        Master m = new Master();
        m.go();
    }
    public void go(){
        Slave s = new Slave(this);
        Thread t1 = new Thread(s);
        t1.start();
        while(bContinue==false){
        }
        s.setPrice(200);
    }
}

class Slave implements Runnable{
    int iPrice =100;
    Master master;
    Slave(Master m){
        master=m;
    }
    synchronized public void setPrice(int iM){
        iPrice=iM;
    }

    synchronized public void run(){
        master.bContinue=true;
        while(true){
            System.out.println(iPrice);
        }

    }
}

