package com.scjp.test;

import junit.framework.TestCase;

//please see this link:  http://stackoverflow.com/questions/3637936/java-integer-equals-vs
public class IntegerIntEqualsTest extends TestCase {

	public void testIntegerEqualsInt() {
		Integer i1 = new Integer(100);
		int i2 = 100;

		assertTrue(i1.equals(i2));
	}

	public void testIntegerIntSamePointer() {
		Integer i1 = new Integer(100);
		int i2 = 100;

		assertEquals(i1 == i2, true);
		// get this, because JVM is caching Integer values. == only works for
		// numbers between -128 and 127
		
	}

	public void testIntegerEqualsInteger() {
		Integer i1 = new Integer(100);
		Integer i3 = new Integer(100);

		assertTrue(i1.equals(i3));

	}

	public void testIntegerIntegerNotSamePointer() {
		Integer y1 = new Integer(345);
		Integer y2 = new Integer(345);

		assertEquals(y1 == y2, false);
		// get this, because JVM is caching Integer values. == only works for
		// numbers between -128 and 127

	}
}
