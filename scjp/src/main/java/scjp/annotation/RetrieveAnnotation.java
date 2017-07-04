//  it comes from :
// http://www.javaeye.com/topic/36659
package scjp.annotation;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class RetrieveAnnotation {

    public static void main(String[] args) throws Exception {
    	
    	ApplyAnnotation one =  new ApplyAnnotation();
    	
    	getInfoOnClass(one);
    	
    	System.out.println("   \r\n============ now try getInfoOnIntrospector=========\r\n");
    	
    	getInfoOnIntrospector(one);    	
    	
    	System.out.println("   \r\n============ now try getInfoOnInterface=========\r\n");
    	
    	getInfoOnInterface(one);
    }
    
    private static void getInfoOnClass(ApplyAnnotation left) throws ClassNotFoundException {
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
            
            System.out.println("  annotation Name originate: " + name.originate());
            System.out.println("  annotation Name community: " + name.community());
            System.out.println("  annotation Name number: " + name.number());				

        }
    	
    }
    
    
    private static void getInfoOnIntrospector(ApplyAnnotation left) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
    	BeanInfo beanInfo;
    	beanInfo = Introspector.getBeanInfo(ApplyAnnotation.class);
    	PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
    	
		for(PropertyDescriptor prop:props){
			Method getter = prop.getReadMethod();
			if(getter==null){
				continue;
			}
			String getterName=getter.getName();
			String fieldName=getterName.startsWith("get")?
					getterName.substring(3): //method name is format of getXXX
					getterName.substring(2); //method name is format of isXXX
			
			Object localValue=getter.invoke(left);
			System.out.println(fieldName + " = " + localValue);
			
			Name name = getter.getAnnotation(Name.class);
			if(name != null){
	            System.out.println("  annotation Name originate: " + name.originate());
	            System.out.println("  annotation Name community: " + name.community());
	            System.out.println("  annotation Name number: " + name.number());				
			}
			
		}

		
    }
    
    private static void getInfoOnInterface(ApplyAnnotation left) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
    	
    	if(!( left instanceof ApplyAnnotationInterface)){
    		throw new RuntimeException("ApplyAnnotation must implement ApplyAnnotationInterface");
    	}
    	BeanInfo beanInfo;
    	beanInfo = Introspector.getBeanInfo(ApplyAnnotationInterface.class);
    	PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
    	
		for(PropertyDescriptor prop:props){
			Method getter = prop.getReadMethod();
			if(getter==null){
				continue;
			}
			String getterName=getter.getName();
			String fieldName=getterName.startsWith("get")?
					getterName.substring(3): //method name is format of getXXX
					getterName.substring(2); //method name is format of isXXX
			
			Object localValue=getter.invoke(left);
			System.out.println(fieldName + " = " + localValue);
			
			Name name = getter.getAnnotation(Name.class);
			if(name != null){
	            System.out.println("  annotation Name originate: " + name.originate());
	            System.out.println("  annotation Name community: " + name.community());
	            System.out.println("  annotation Name number: " + name.number());				
			}
			
		}

		
    }

}  
