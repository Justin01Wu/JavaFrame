package scjp.testcollection;

import java.util.HashMap;

public class TestMapMerge {
	
    public static void main(String[] args){    
        HashMap<String, Integer> a=new HashMap<>();
        
        // test merge
        String key = "Test1"; 
        a.put(key,1);
        a.merge(key,3, (oldValue, newValue) -> oldValue + newValue);
        System.out.println(a.get(key));  // will output "4"

        
        // test merge
        a.put(key,2);
        a.merge(key,3, (oldValue, newValue) -> newValue * oldValue );
        System.out.println(a.get(key));  // will output "6"

    }
}
