package com.justa.springboot.db.converter2;

import com.justa.springboot.db.PersistableEnum;

public enum IntegerEnum implements PersistableEnum<Integer> {
    Male(10), 
    Female(20);

    private final Integer value;

    @Override
    public Integer getValue() {
        return value;
    }
    
    public static IntegerEnum fromInt(Integer value) {
    	if(value == null) {
    		return null;
    	}
    	for(IntegerEnum one: IntegerEnum.values()) {
    		if(one.getValue().equals(value)) {
    			return one;
    		}
    	}
        return null;
    }
    
	@Override
	public IntegerEnum getEnum(Integer value) {
		return fromInt(value);
	}

    private IntegerEnum(Integer value) {
        this.value= value;
    }

    public static class Converter extends AbstractEnumConverter<IntegerEnum, Integer> {
        public Converter() {
            super(IntegerEnum.class);
        }
    }
}
