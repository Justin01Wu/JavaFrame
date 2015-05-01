/*
 * TrafficLight3.java
 *
 * Created on January 12, 2008, 6:52 PM
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
public class TrafficLight3 {
    
    private LightState3 state;
    public LightState3 getState() {
        return state;
    }
    
    public void setState(LightState3 state) {
        this.state=state;
    }    
    
}
