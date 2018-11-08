package scjp.clone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.junit.Test;

public class BeanATest  {

	@Test
	public void testCopyBeanA() throws CloneNotSupportedException {
		
		Date date = new Date(1541692027152l);  // 2018 Nov 08 10:47:07
		
		BeanA o1 = new BeanA();
		o1.setId(2348);
		o1.setName("Justin123");
		o1.setBirthDate(date);
		
		BeanA v2  = o1.clone();
		assertTrue(v2 != o1);
		assertEquals(v2.getId(), 2348);
		assertEquals(v2.getName(), "Justin123-cloned");
		
		assertEquals(v2.getBirthDate().getTime(), 1541692027152l);
		
		o1.getBirthDate().setTime(3495834l);
		assertEquals(v2.getBirthDate().getTime(), 3495834l);  // shallow copy
		
		
	}
	
	@Test
	public void testDeepCopyBeanA() throws ClassNotFoundException, IOException {
		
		Date date = new Date(1541692027152l);  // 2018 Nov 08 10:47:07
		
		BeanA o1 = new BeanA();
		o1.setId(2348);
		o1.setName("Justin123");
		o1.setBirthDate(date);
		
		BeanA v2  = o1.deepClone();
		assertTrue(v2 != o1);
		assertEquals(v2.getId(), 2348);
		assertEquals(v2.getName(), "Justin123");
		
		assertEquals(v2.getBirthDate().getTime(), 1541692027152l);
		
		o1.getBirthDate().setTime(3495834l);
		assertEquals(v2.getBirthDate().getTime(), 1541692027152l);  // deep copy
		
		
	}

	
	@Test
	public void testCopyBeanB() throws CloneNotSupportedException {
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
	public void testDeepCopyBeanB() throws ClassNotFoundException, IOException {
		
		Date date = new Date(1541692027152l);  // 2018 Nov 08 10:47:07
		
		BeanB o1 = new BeanB();
		o1.setId(2348);
		o1.setName("Justin123");
		o1.setTId(1323);
		o1.setBirthDate(date);

		BeanB v2  = o1.deepClone();
		assertTrue(v2 != o1);
		assertEquals(v2.getId(), 2348);
		assertEquals(v2.getName(), "Justin123");
		assertEquals(v2.getTId(), new Integer(1323));
		
		assertEquals(v2.getBirthDate().getTime(), 1541692027152l);
		
		o1.getBirthDate().setTime(3495834l);
		assertEquals(v2.getBirthDate().getTime(), 1541692027152l);  // deep copy

		
	}

	
	@Test
	public void testBrokenArrayCopy() throws CloneNotSupportedException {
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
	
	@Test
	public void testFixedArrayCopy() throws CloneNotSupportedException, IOException, ClassNotFoundException {
		int[][] matrix1 = new int[2][2];
		matrix1[0][0] = 345;
		matrix1[1][1] = 456;		
		
		// use Serializable to implement deep copy
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(matrix1);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        int[][] matrix1Clone = (int[][]) ois.readObject();
		
		assertEquals(matrix1Clone[0][0], 345);	 
		assertEquals(matrix1Clone[1][1], 456);
		
		
		matrix1[0][0] = 999;
		assertEquals(matrix1Clone[0][0], 345); // it is expected now
		
		// comes from https://stackoverflow.com/questions/9737182/bug-in-using-object-clone
		
	}

}
