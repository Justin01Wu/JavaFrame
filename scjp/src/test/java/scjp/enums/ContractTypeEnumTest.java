package scjp.enums;

import static org.junit.Assert.*;

import org.junit.Test;

public class ContractTypeEnumTest {

	@Test
	public void test() {
		ContractTypeEnum type = LookupById.lookupById2(ContractTypeEnum.class, 2);
		assertEquals(ContractTypeEnum.TypeB, type);
	}

}
