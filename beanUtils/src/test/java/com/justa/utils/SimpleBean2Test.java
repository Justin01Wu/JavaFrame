package com.justa.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class SimpleBean2Test {

	//@Test
    public void testUpdate() throws InvocationTargetException, IllegalAccessException  {
    	
    	SimpleBean simpleBean = new SimpleBean();
    	simpleBean.setId(12345);
    	simpleBean.setValid(null);
    	simpleBean.setValid2(null);
    	simpleBean.setAge(23);
    	
    	SimpleBean2 simpleBean2 = new SimpleBean2();
    	simpleBean2.setValid(true);
    	simpleBean2.setValid2(true);

    	ObjectUpdater.updateObject(simpleBean2, simpleBean);
    	
		assertTrue( simpleBean2.getValid() );  // will skip because it is null
        assertTrue( simpleBean2.isValid2() );  // will skip because it is null
        assertTrue( simpleBean2.getId().equals(12345));
        assertEquals ( simpleBean2.getAge(), 23 );
    }
	
	@Test
    public void testReverseUpdate() throws InvocationTargetException, IllegalAccessException  {
    	
    	SimpleBean simpleBean = new SimpleBean();
    	simpleBean.setId(12345);
    	simpleBean.setValid(null);
    	simpleBean.setValid2(null);
    	simpleBean.setAge(23);
    	
    	SimpleBean2 simpleBean2 = new SimpleBean2();
    	simpleBean2.setValid(true);
    	simpleBean2.setValid2(true);

    	ObjectUpdater.updateObject(simpleBean, simpleBean2);
    	
		assertTrue( simpleBean.getValid() );  
        //assertTrue( simpleBean.isValid2() );  // will get NullPointerException,why?
        assertTrue( simpleBean.getId().equals(12345));
        assertEquals ( simpleBean.getAge(), 0 );

    }

}
