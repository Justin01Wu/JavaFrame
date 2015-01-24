/*
 * Main.java
 *
 * Created on January 12, 2008, 6:37 PM
 *

 */

package scjp.typesafe;

/**
 *
 * @author justinwu
 */
public class Main {
    
    public static void main(String[] args) {
        
        // this is no type safe sample
        TrafficLight nextTrafficLight =new TrafficLight();
        nextTrafficLight.setState(23);
        
        switch (nextTrafficLight.getState()) {
            case TrafficLight.RED:
                System.out.println("stop");
                //stop();
                break;
            case TrafficLight.YELLOW:
                System.out.println("floor it");
                //floorIt();
                break;
            case TrafficLight.GREEN:
                System.out.println("proceed");
                //proceed();
                break;
            default:
                //assert false: Strange-colored light;
                System.out.println("type set incorrectly!!!!....");
        }
        
        // this is type safe sample without enum
        TestTypeSafe.test();        
        
        // this is enum sample(new feture of JDK 1.5)
        TestEnum3.test();
        
        TestEnum4.test();
    }
}
