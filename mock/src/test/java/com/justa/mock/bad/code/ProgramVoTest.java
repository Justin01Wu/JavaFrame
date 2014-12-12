package com.justa.mock.bad.code;

import static org.junit.Assert.assertEquals;
import mockit.Deencapsulation;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

import com.justa.mock.bad.code.JSFUtilTest.JSFUtilMock;

//it comes from http://jmockit.googlecode.com/svn/trunk/www/tutorial/StateBasedTesting.html
public final class ProgramVoTest {

	// A reusable mock-up class to be applied in specific tests.
	public static final class ProgramVoMock extends MockUp<ProgramVo> {
		
		@Mock
		void $clinit() {   // this line means we skip static block
			System.out.println("Static is bypassed");
		}   
		
		@Mock(invocations = 1)
		void $init(Invocation inv) {   // this line means we mock constructor ProgramVo(){}
			System.out.println("default constructor is bypassed");
			ProgramVo programVo = inv.getInvokedInstance();
			System.out.println("   ProgramVo ->  going to inject mocked jSFUtil");
			
			new JSFUtilMock();  // start mock magic 
			JSFUtil jSFUtil = new JSFUtil();
	        Deencapsulation.setField( programVo, "jsfUtils", jSFUtil);
	        System.out.println("   ProgramVo ->  injected jSFUtil");

		}   
		
		@Mock
		public String getName() {
			return "name6789";
		}
	}

	public static ProgramVo getProgramVoMockWithId24567(){
		ProgramVo p = getProgramVoMockWithNullId();
		p.setId(24567);
		return p;
	}
	
	public static ProgramVo getProgramVoMockWithNullId(){
		new ProgramVoMock();		
		ProgramVo p = new ProgramVo();
		return p;
	}
	
	@Test
	public void test() {
		
		ProgramVo p = getProgramVoMockWithId24567();
		assertEquals(p.getClass().getName(), "com.justa.mock.bad.code.ProgramVo");

		assertEquals(p.getId(), Integer.valueOf(24567)); // why? because getId method is not mocked
		
		p.setName("dffg");
		assertEquals(p.getName(), "name6789"); // why? because getName method is mocked, but setName is not mocked
		assertEquals(p.getDisplayName(), "mocked name"); // why? because getDisplayName method is mocked in JSFUtilTest
	}
	
	public class ProgramVo2 extends ProgramVo{
		public ProgramVo2(){			
		}
	}


}

