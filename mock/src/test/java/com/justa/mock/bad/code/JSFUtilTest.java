package com.justa.mock.bad.code;

import static org.junit.Assert.*;
import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

public class JSFUtilTest {

	// A reusable mock-up class to be applied in specific tests.
	public static final class JSFUtilMock extends MockUp<JSFUtil> {
		
		@Mock(invocations = 1)
		void $init() {   // this line means we mock constructor JSFUtil(){}
			
		}
		
		@Mock
		public String getName() {
			return "mocked name";
		}
	}
	
	@Test
	public void test() {
		
		new JSFUtilMock();  // start mock magic
		JSFUtil jSFUtil = new JSFUtil();
		assertEquals(jSFUtil.getName(), "mocked name"); // why? because getDisplayName method is mocked in JSFUtilTest
	}

}
