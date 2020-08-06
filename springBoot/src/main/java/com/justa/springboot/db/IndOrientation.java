package com.justa.springboot.db;

//from https://stackoverflow.com/questions/23564506/is-it-possible-to-write-a-generic-enum-converter-for-jpa
public enum IndOrientation implements PersistableEnum<String> {
    LANDSCAPE("L"), 
    PORTRAIT("P");

    private final String value;

    @Override
    public String getValue() {
        return value;
    }
    
    public static IndOrientation fromShortName(String shortName) {
    	if(shortName == null) {
    		return null;
    	}
    	for(IndOrientation one: IndOrientation.values()) {
    		if(one.getValue().equals(shortName)) {
    			return one;
    		}
    	}
        return null;
    }
    
	@Override
	public IndOrientation getEnum(String shortName) {
		return fromShortName(shortName);
	}

    private IndOrientation(String value) {
        this.value= value;
    }

    public static class Converter extends AbstractEnumConverter<IndOrientation, String> {
        public Converter() {
            super(IndOrientation.class);
        }
    }
}
