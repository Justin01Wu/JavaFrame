package scjp.enums;

public enum ContractTypeEnum implements LookupById<ContractTypeEnum> {
	UNKNOWN(0, "unknown"),
	TypeA(1, "Type A"),
	TypeB(2, "Type B");
	
	private final int contractTypeId;
	private final String contractTypeName;
	
	private ContractTypeEnum(int contractTypeId, String contractTypeName){
		this.contractTypeId =contractTypeId;
		this.contractTypeName = contractTypeName;
	}
	
	@Override
	public int getId() {
		return contractTypeId;
	}
	
	public String getContractTypeName(){
		return contractTypeName;
	}
}
