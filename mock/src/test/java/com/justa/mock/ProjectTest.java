package com.justa.mock;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.justa.mock.bad.code.ProgramVo;
import com.justa.mock.bad.code.ProgramVoTest;

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

