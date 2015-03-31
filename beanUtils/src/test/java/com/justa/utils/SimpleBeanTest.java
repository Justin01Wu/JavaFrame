package com.justa.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
/**
 * Unit test for simple App.
 */
public class SimpleBeanTest {

	@Test
    public void testCopy() throws IllegalAccessException, InvocationTargetException   {
    	
    	SimpleBean simpleBean = new SimpleBean();
    	simpleBean.setId(12345);
    	simpleBean.setValid(null);
    	simpleBean.setValid2(null);
    	simpleBean.setAge(23);
    	
    	SimpleBean simpleBean2 = new SimpleBean();
    	simpleBean2.setValid(true);
    	simpleBean2.setValid2(true);

		BeanUtils.copyProperties(simpleBean2, simpleBean);
    	
		assertTrue( simpleBean2.getValid() == null );
        assertTrue( simpleBean2.isValid2() == null );  // this one will fail because it is 'is' style name
        assertTrue( simpleBean2.getId().equals(12345));
        assertEquals ( simpleBean2.getAge(), 23 );
    }
}
