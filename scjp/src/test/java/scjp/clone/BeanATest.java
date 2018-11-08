package scjp.clone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class BeanATest  {

	@Test
	public void testCopyDeal() throws CloneNotSupportedException {
		Date date = new Date();
		
		BeanA o1 = new BeanA();
		o1.setId(2348);
		o1.setName("Justin123");
		o1.setBirthDate(date);
		
		BeanA v2  = o1.clone();
		assertTrue(v2 != o1);
		assertEquals(v2.getId(), 2348);
		assertEquals(v2.getName(), "Justin123-cloned");
		
	}
	
	@Test
	public void testCopyDealCommonDTO() throws CloneNotSupportedException {
		BeanB o1 = new BeanB();
		o1.setId(2348);
		o1.setName("Justin123");
		o1.setTId(1323);

		BeanB v2  = o1.clone();
		assertTrue(v2 != o1);
		assertEquals(v2.getId(), 2348);
		assertEquals(v2.getName(), "Justin123-cloned");
		assertEquals(v2.getTId(), new Integer(1323));
		
	}
	
	@Test
	public void testArrayCopy() throws CloneNotSupportedException {
		int[][] matrix1 = new int[2][2];
		matrix1[0][0] = 345;
		matrix1[1][1] = 456;
		int[][] matrix1Clone = matrix1.clone();
		
		assertEquals(matrix1Clone[0][0], 345);	 
		assertEquals(matrix1Clone[1][1], 456);
		
		
		matrix1[0][0] = 999;
		assertEquals(matrix1Clone[0][0], 999); // it is a little surprise!!!
		
		// comes from https://stackoverflow.com/questions/9737182/bug-in-using-object-clone
		
	}
}
