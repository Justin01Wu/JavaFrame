/*
 * LightState.java
 *
 * Created on January 12, 2008, 6:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scjp.typesafe;

/**
 *
 * @author justinwu
 */
public class LightState2 {
    
    protected final String name;
    
    public final static LightState2 RED = new LightState2("red");
    public final static LightState2 YELLOW = new LightState2("yellow");
    public final static LightState2 GREEN = new LightState2("green");
    
    private LightState2(String s) {
        name = s;
    }
    
    public String name() {
        return name;
    }
}
