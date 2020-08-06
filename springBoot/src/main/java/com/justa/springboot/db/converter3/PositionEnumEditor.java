package com.justa.springboot.db.converter3;

import java.beans.PropertyEditorSupport;

import com.justa.springboot.db.PositionEnum;


//from  https://stackoverflow.com/questions/7730529/overriding-beanpropertyrowmapper-to-support-jodatime-datetime
public class PositionEnumEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
    	PositionEnum v = PositionEnum.fromShortName(text);
        setValue(v);                     
    }
    @Override
    public void setValue(final Object value) {
    	if(value == null) {
    		super.setValue(null);
    		return;
    	}
    	if(value instanceof PositionEnum) {
    		super.setValue(value);  
    		return;
    	}
    	throw new IllegalArgumentException("Unknown type:" + value.getClass().getSimpleName());
    }
    @Override
    public PositionEnum getValue() {
        return (PositionEnum) super.getValue();
    }
    @Override
    public String getAsText() {
        return getValue().toString(); 
                                      
    }

}
