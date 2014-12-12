package com.justa.mock;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.justa.mock.bad.code.ProgramVo;
import com.justa.mock.bad.code.ProgramVoTest;

/** this will demo how to use Jmockit to mock toughest Java code in unit test <br/> 
Jmockit is the most powerful Java mock up framework<br/>
 
<ul>   
	<li>can mock static method and block</li>
	<li>mocks static and final methods, constructors, and future instances</li>
	<li>mocks final classes, enums, annotations, and multiple interfaces</li>
	<li>mocks implementation classes given just the interface or base class</li>	
</ul>

For details, please see:  
<a href="http://jmockit.github.io/tutorial.html"> here</a>

<h1 style="color:red">you need to set javaagent to run unit test in Eclipse or IDE<br/> 
Please follow <a href="http://jmockit.github.io/tutorial/Introduction.html#runningTests">this</a> to set up
</h1>

* 
* */
public class ProjectTest {

	@Test
	public void testCreateProgramWithId() {

		ProgramVo p = ProgramVoTest.getProgramVoMockWithId24567();

		p.setName("sss");
		assertEquals(p.getName(), "name6789"); // why? because getName method is
												// mocked

		assertEquals(p.getId(), Integer.valueOf(24567));

	}

	@Test
	public void testCreateProgramWithNullId() {

		ProgramVo p = ProgramVoTest.getProgramVoMockWithNullId();

		p.setName("sss");
		assertEquals(p.getName(), "name6789"); // why? because getName method is
												// mocked

		assertEquals(p.getId(), null);

	}

//	@Mocked
//	private ProgramVo programVoMock;
//
//	@Test
//	public void testCreateProgramWithNewMockId() {
//
//		new NonStrictExpectations() {
//			{
//				ProgramVoTest.getProgramVoMockWithNullId();
//				minTimes = 1;
//				result = programVoMock;
//			}
//		};
//		ProgramVoTest.getProgramVoMockWithNullId();
//
//		new NonStrictExpectations() {
//			{
//				programVoMock.getId();
//				minTimes = 1;
//				result = 1234567;
//			}
//		};
//		assertEquals(programVoMock.getId(), Integer.valueOf(1234567));
//
//	}

}

