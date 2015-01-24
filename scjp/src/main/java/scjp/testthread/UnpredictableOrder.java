// What will happen when you attempt to compile and run the following code?
/*
1) Compile time error
2) Compilation and output of "vandeleur wiggy"
3) Compilation and output of "vandeleur wiggy 0 1 2 3"
4) Compilation and probably output of "vandelur" but possible output of "vandeleur 0 1 2 3"
The answer is 4
 */

package scjp.testthread;

public class UnpredictableOrder extends Thread {

    static String sName = "vandeleur";

    public static void main(String argv[]) {
        UnpredictableOrder t = new UnpredictableOrder();
//      t.piggy2(sName); //this method can NOT change value of static variable
//      t.piggy3();      // this method CAN change value of static variable
        t.piggy(sName);
        System.out.println(sName);
    }

    public void piggy2(String sName) {
        sName = sName + " wiggy--2--2";
    }

    public void piggy3() {
        sName = sName + " wiggy--3--3";
    }

    public void piggy(String sName) {
        sName = sName + " wiggy";
        start();
    }

    public void run() {
        for (int i = 0; i < 4; i++) {
            sName = sName + " " + i;
        }
    }
    
}

