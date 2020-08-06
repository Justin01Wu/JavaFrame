package com.justa.springboot.db;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.StringUtils;

public class ExtendedBeanPropertyRowMapper <T> extends BeanPropertyRowMapper<T> {
    public ExtendedBeanPropertyRowMapper(Class<T> class1) {
        super(class1);
    }
    
    // from https://stackoverflow.com/questions/15411843/spring-jdbc-beanpropertyrowmapper-yes-no-y-n-to-boolean-bean-properties
    @Override
    protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
        Class<?> requiredType = pd.getPropertyType();
        if (PositionEnum.class.equals(requiredType) ) {
            String stringValue = rs.getString(index);
            if(!StringUtils.isEmpty(stringValue) ){
            	return PositionEnum.fromShortName(stringValue);
            }
            else return null;
        }       
        return super.getColumnValue(rs, index, pd);
    }
}
