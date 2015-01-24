/*
 * LightState21.java
 *
 * Created on January 14, 2008, 10:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scjp.typesafe;

/**
 *
 * @author justinwu
 */
public class LightState21 {
    
    protected final int value;

    public final static int RED_COLOR = 231;
    public final static int YELLOW_COLOR = 232;
    public final static int GREEN_COLOR = 233;    
    
    public final static LightState21 RED = new LightState21(RED_COLOR);
    public final static LightState21 YELLOW = new LightState21(YELLOW_COLOR);
    public final static LightState21 GREEN = new LightState21(GREEN_COLOR);
    
    private LightState21(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
}
