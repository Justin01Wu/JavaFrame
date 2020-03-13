package com.justa.springboot.db;

public enum PositionEnum {
	
	Developer("D"),
	QA("Q"),
	Manager("M");
	
	private String shortName;
	private PositionEnum(String shortName) {
        this.shortName = shortName;
    }
 
    public String getShortName() {
        return shortName;
    }
 
    public static PositionEnum fromShortName(String shortName) {
    	if(shortName == null) {
    		return null;
    	}
        switch (shortName) {
        case "D":
            return PositionEnum.Developer; 
        case "Q":
            return PositionEnum.QA; 
        case "M":
            return PositionEnum.Manager;  
        default:
            return null;
        }
    }
}
