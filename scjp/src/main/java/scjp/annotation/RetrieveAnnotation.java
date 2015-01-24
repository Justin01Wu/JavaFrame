//  it comes from :
// http://www.javaeye.com/topic/36659
package scjp.annotation;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class RetrieveAnnotation {

    public static void main(String[] args) throws Exception {
        String CLASS_NAME = "scjp.annotation.ApplyAnnotation";
        Class<?> test = Class.forName(CLASS_NAME);
        
        ApplyAnnotation test2 = new ApplyAnnotation();
        
        System.out.println(test.getName());
        System.out.println(test2.getClass().getName());
        
        Method[] method = test.getMethods();
        boolean flag = test.isAnnotationPresent(Description.class);
        if (flag) {
            Description des = (Description) test.getAnnotation(Description.class);
            System.out.println("Description:" + des.value());
            System.out.println("-----------------");
        }

        //put all method with @Name into a Set   
        Set<Method> set = new HashSet<Method>();
        for (int i = 0; i < method.length; i++) {
            boolean otherFlag = method[i].isAnnotationPresent(Name.class);
            if (otherFlag) {
                set.add(method[i]);
            }
        }
        for (Method m : set) {
            Name name = m.getAnnotation(Name.class);
            System.out.println("=============");
            System.out.println(name.originate());
            System.out.println("community:" + name.community());
            System.out.println("number:" + name.number());
            
        }
    }
}  
