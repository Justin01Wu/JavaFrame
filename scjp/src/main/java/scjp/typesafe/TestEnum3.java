/*
 * TestEnum3.java
 *
 * Created on January 14, 2008, 10:36 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scjp.typesafe;

import scjp.typesafe.Enums.LightState3;

/**
 *
 * @author justinwu
 */
public class TestEnum3 {
    
    public static void test() {
        // this is enum sample(new feature of JDK 1.5)
        TrafficLight3 nextTrafficLight3 =new TrafficLight3();
        nextTrafficLight3.setState(LightState3.GREEN);
        LightState3 state3=nextTrafficLight3.getState();
        System.out.println(state3);
        System.out.println( state3.ordinal() );  // Prints 2. ordinals are 0-based s.
        
        // converting int to enum
        // LightState3 0 is RED.
        LightState3 theState = LightState3.values()[0];
        System.out.println( theState );
        
        // Example iterating over all possibilities.
        // Print out a list of all possible breeds.
        System.out.println( "All possible LightState3:" );
        for ( LightState3 statexx : LightState3.values() )
            {
            System.out.println( statexx );
            }        
        
        // converting from String to enum, peculiar but slick
        // saves reams of ifs or hashMap lookups.
        // The compiler magically generates this method for you.
        // You will not find it is the base Enum class.
        // Works for any String matching one of the breeds.
        System.out.println( "set by a string:" );
        LightState3 xx = LightState3.valueOf( "Yellow".toUpperCase() );
       System.out.println( xx );        
       
        System.out.println( "set by a customized string:" );
        LightState3 yy = LightState3.getInstance("Yel" );
        System.out.println( yy );        
        
        switch (nextTrafficLight3.getState()) {
            case RED :  // use LightState3.RED cause a compile error, strange?
                System.out.println("stop");
                break;
            case YELLOW :
                System.out.println("floor it");
                break;
            case GREEN :
                System.out.println("proceed");
                break;
            default:
                //assert false: "null light state";
                System.out.println("null light state.!!!!");
        }
        
        
        
    }
    
    public static void main(String[] args) {
        test();
    }      
    
}
