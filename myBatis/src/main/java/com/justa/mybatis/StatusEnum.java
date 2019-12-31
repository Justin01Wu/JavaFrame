package com.justa.mybatis;

public enum StatusEnum {
	Active("A"),
	Inactive("I");
	
	private final String value;
	StatusEnum(String value){
		this.value = value;		
	}
	
	public String getValue() {
		return value;
	}
	
	public static StatusEnum getEnum(String v) {
		for(StatusEnum one : StatusEnum.values()) {
			if(v.equals(one.name())) {
				return one;
			}
		}
		return null;		
		
	}

}
