// this show advance generic usage
// it comes from 
//http://www.cnblogs.com/jobs/archive/2007/11/15/959802.html
package scjp.generic;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void addAll(List<BaseClass> items) {
    }

    public static void addAll2(List<? extends BaseClass> items) {
    }

    public static void main(String[] args) {
        List<BaseClass> aList = new ArrayList<>();

        List<ExtendedClass> bList = new ArrayList<>();

        addAll(aList); //can compile

//addAll(bList); //can't compile        

        addAll2(bList); //can compile        
    }
}
