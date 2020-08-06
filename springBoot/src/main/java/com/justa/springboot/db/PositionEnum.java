package com.justa.springboot.db;

public enum PositionEnum {
	
	Developer("D"),
	QA("Q"),
	Manager("M");
	
	private String shortName;
	private PositionEnum(String shortName) {
        this.shortName = shortName;
    }
 
    public static PositionEnum fromShortName(String shortName) {
    	if(shortName == null) {
    		return null;
    	}
    	for(PositionEnum one: PositionEnum.values()) {
    		if(one.getShortName().equals(shortName)) {
    			return one;
    		}
    	}
        return null;
    }    

	public String getShortName() {
		return shortName;
	}
}
