/*
 * TrafficLight2.java
 *
 * Created on January 12, 2008, 6:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scjp.typesafe;

/**
 *
 * @author justinwu
 */
public class TrafficLight2 {
    
    private LightState2 state;
    public LightState2 getState() {
        return state;
    }
    
    public void setState(LightState2 state) {
        this.state=state;
    }    
    
}
