/*
 * TestTypeSafe.java
 *
 * Created on January 14, 2008, 10:38 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scjp.typesafe;

/**
 *
 * @author justinwu
 */
public class TestTypeSafe {
    
    public static void test() {
        // this is type safe sample without enum
        TrafficLight2 nextTrafficLight2 =new TrafficLight2();
        LightState2 state2 = nextTrafficLight2.getState();
        if (state2 == LightState2.RED)
            System.out.println("stop");
        else if (state2 == LightState2.YELLOW)
            System.out.println("floor it");
        else if (state2 == LightState2.GREEN)
            System.out.println("proceed");
        else
            //assert false : "null light state.";
            System.out.println("null light state.!!!!");
        
        
        
        // this is another type safe sample without enum
        TrafficLight21 nextTrafficLight21 =new TrafficLight21();
        nextTrafficLight21.setState(LightState21.GREEN);
        LightState21 state21 = nextTrafficLight21.getState();
        switch (state21.getValue()){
            case LightState21.RED_COLOR:
                System.out.println("do red proceding");
                break;
            case LightState21.YELLOW_COLOR:
                System.out.println("do yellow proceding...");
                break;
            case LightState21.GREEN_COLOR:
                System.out.println("do green proceding...");
                break;
            default:
                //assert false : "null light state.";
                System.out.println("null light state.!!!!");
        }
        
    }
    
    
    public static void main(String[] args) {
        test();
    }      
}
