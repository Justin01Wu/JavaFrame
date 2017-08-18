package scjp.enums;

public enum ContractTypeEnum implements LookupById {
	UNKNOWN(0, "unknown"),
	TypeA(1, "Type A"),
	TypeB(2, "Type B");
	
	private final int contractTypeId;
	private final String contractTypeName;
	
	private ContractTypeEnum(int contractTypeId, String contractTypeName){
		this.contractTypeId =contractTypeId;
		this.contractTypeName = contractTypeName;
	}
	
	public int getContractTypeId() {
		return contractTypeId;
	}
	
	@Override
	public int getId() {
		return contractTypeId;
	}
	
	/**
	 * Gets the contract type name.
	 * 
	 * @return the contract type name
	 */
	public String getContractTypeName(){
		return contractTypeName;
	}
}
