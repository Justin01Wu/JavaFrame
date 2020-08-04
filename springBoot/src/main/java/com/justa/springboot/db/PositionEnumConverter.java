package com.justa.springboot.db;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

// from https://thoughts-on-java.org/jpa-21-type-converter-better-way-to/

// TODO please follow the below url to get generic enum converter
//  https://stackoverflow.com/questions/23564506/is-it-possible-to-write-a-generic-enum-converter-for-jpa

@Converter(autoApply = true)   // tell the JPA provider to use it to map PositionEnum in any entities
public class PositionEnumConverter implements AttributeConverter<PositionEnum, String> {
 
    @Override
    public String convertToDatabaseColumn(PositionEnum position) {
    	if(position == null) {
    		return null;
    	}
        return position.getShortName();
    }
 
    @Override
    public PositionEnum convertToEntityAttribute(String dbData) {
        return PositionEnum.fromShortName(dbData);
    }
 
}