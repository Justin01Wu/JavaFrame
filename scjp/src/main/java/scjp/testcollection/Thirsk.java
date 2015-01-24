/*What will happen when you attempt to compile and run the following code?
 1) Compile time error, a19 cannot be used as a method name
 2) Compilation and output of BillKathyCarl
 3) Compilation and output of BillCarlKathy
 4) Compilation and output of abc
 The answer is 2
*/
package scjp.testcollection;

import java.util.*;
public class Thirsk{
    public static void main(String argv[]){
        Thirsk t = new Thirsk();
        t.a19();
    }
    public void a19(){
        TreeMap tm = new TreeMap();
        tm.put("a","Bill");
        tm.put("b","Kathy");
        tm.put("c","Carl");
        Iterator it = tm.keySet().iterator();
        while(it.hasNext()){
            System.out.print(tm.get(it.next()));
        }
    }
}

