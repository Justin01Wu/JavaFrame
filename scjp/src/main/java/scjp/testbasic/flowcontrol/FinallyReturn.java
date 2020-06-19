/*  it come from :
 * http://blog.csdn.net/SmartTony/archive/2008/01/24/2062295.aspx
 * Guess what will be returned from t.get() ?
 *   2 or 4 ? the answer is 4
 */
package scjp.testbasic.flowcontrol;

public class FinallyReturn {

    public static void main(String args[]) {
        FinallyReturn t = new FinallyReturn();
        int b = t.get();
        System.out.println("the answer is :  " + b);
    }

    public int get() {
        try {
            System.out.println("general return :  " + 2);
            return 2;
        } catch (Exception e) {
            System.out.println(3);
        } finally {
            System.out.println("finally return :  " + 4);
            return 4;
        }
    }
    
}


