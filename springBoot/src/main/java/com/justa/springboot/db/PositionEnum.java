package com.justa.springboot.db;

public enum PositionEnum implements PersistableEnum<String>{
	
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
    		if(one.getValue().equals(shortName)) {
    			return one;
    		}
    	}
        return null;
    }
    
	@Override
	public PositionEnum getEnum(String shortName) {
		return fromShortName(shortName);
	}

	@Override
	public String getValue() {
		return shortName;
	}
}
