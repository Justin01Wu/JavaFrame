
// see compile error: duplicate case label
package scjp.testbasic.flowcontrol;


public class TestSwitch2 {
    public static void main(String argv[]) {
        byte k=2;
        switch (k) {
            default:
                System.out.println("unknown k");
                break;
            case 2:
                System.out.println("k==2");
                break;
            //case 2:  // compile error: duplicate case label
                //System.out.println("k equal to 2");                
            case 3:
                System.out.println("k==3");
                break;
        }
    }

}
