/*
 * TrafficLight.java
 *
 * Created on January 12, 2008, 6:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scjp.typesafe;

/**
 *
 * @author justinwu
 */
public class TrafficLight {
    public final static int RED = 1;
    public final static int YELLOW = 2;
    public final static int GREEN = 3;
    
    private int state;
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state=state;
    }
    
    

    
    
}
