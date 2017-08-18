package scjp.enums;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import scjp.typesafe.LightState4;

public class ContractTypeEnumTest {

	@Test
	public void test() {
		ContractTypeEnum type = LookupById.lookupById2(ContractTypeEnum.class, 2);
		assertEquals(ContractTypeEnum.TypeB, type);
		
		//LightState4 state = LookupById.lookupById2(ContractTypeEnum.class, 2); // compile error:   LightState4 is not ContractTypeEnum
		
		//LightState4 state = LookupById.lookupById2(LightState4.class, 2); // compile error:  LightState4 didn't implements LookupById
	}

}
