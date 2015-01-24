/*
 * LightState4.java
 *
 * Created on January 14, 2008, 10:09 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scjp.typesafe;

/**
 *
 * @author justinwu
 */
public enum LightState4 {
    RED("red"), YELLOW("yellow"), GREEN("green");
    private final String description;
    LightState4(String description){
        this.description=description;
        
    }
    ///*
    public String toString() {
        return this.description;
    }
    //*/
    
}
