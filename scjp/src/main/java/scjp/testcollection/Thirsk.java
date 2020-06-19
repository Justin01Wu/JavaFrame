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
        TreeMap<String, String> tm = new TreeMap<>();  // TreeMap is sorted on key value
        tm.put("c","Carl");
        tm.put("a","Bill");
        tm.put("b","Kathy");        
        Iterator<String> it = tm.keySet().iterator();
        while(it.hasNext()){
            System.out.print(tm.get(it.next()));
        }
    }
}

