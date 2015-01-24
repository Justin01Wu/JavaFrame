/*
 * TrafficLight21.java
 *
 * Created on January 14, 2008, 10:39 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scjp.typesafe;

/**
 *
 * @author justinwu
 */
public class TrafficLight21 {
    private LightState21 state;
    public LightState21 getState() {
        return state;
    }
    
    public void setState(LightState21 state) {
        this.state=state;
    }   
    
}
