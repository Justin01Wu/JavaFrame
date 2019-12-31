package com.justa.mybatis;

public enum StatusEnum {
	Active(10),
	Inactive(20);
	
	private final int value;
	StatusEnum(int value){
		this.value = value;		
	}
	
	public int getValue() {
		return value;
	}
	
	public static StatusEnum getEnum(Integer v) {
		for(StatusEnum one : StatusEnum.values()) {
			if(v.equals(one.getValue())) {
				return one;
			}
		}
		return null;		
		
	}

}
