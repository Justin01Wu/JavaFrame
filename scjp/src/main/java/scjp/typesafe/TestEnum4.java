/*
 * TestEnum.java
 *
 * Created on January 14, 2008, 10:12 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scjp.typesafe;

/**
 *
 * @author justinwu
 */
public class TestEnum4 {
    
    public static void test() {
        LightState4 xx=LightState4.GREEN;
         System.out.println(xx.toString());
         System.out.println(xx.name());
         
         //LightState4 yy2=new LightState4("green");
         // this cause a compile error?
         
         }
    
    public static void main(String[] args) {
        test();
    }    
    
}
