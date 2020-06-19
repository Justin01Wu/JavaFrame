/*
/*What is the output when you run the following code?
 The answer is "{Test1=Test1, Test2=Test2}"
 */

package scjp.testcollection;

import java.util.HashMap;

public class TestHashMap {
    
    public static void addEntry(HashMap<String, String> a){
        a.put("Test2","Test0");
        a.put("Test2","Test2");
        a=null;
    }
    public static void main(String[] args){    
        HashMap<String, String> a=new HashMap<>();
        a.put("Test1","Test1");
        addEntry(a);
        System.out.println(a);
    }
        
    
}
