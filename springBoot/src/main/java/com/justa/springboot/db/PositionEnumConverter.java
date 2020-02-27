package com.justa.springboot.db;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)   // tell the JPA provider to use it to map PositionEnum in any entity
public class PositionEnumConverter implements AttributeConverter<PositionEnum, String> {
 
    @Override
    public String convertToDatabaseColumn(PositionEnum position) {
        return position.getShortName();
    }
 
    @Override
    public PositionEnum convertToEntityAttribute(String dbData) {
        return PositionEnum.fromShortName(dbData);
    }
 
}