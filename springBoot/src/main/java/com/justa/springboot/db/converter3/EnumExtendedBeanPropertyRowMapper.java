package com.justa.springboot.db.converter3;

import org.springframework.beans.BeanWrapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.justa.springboot.db.PositionEnum;

//please see https://stackoverflow.com/questions/7730529/overriding-beanpropertyrowmapper-to-support-jodatime-datetime
public class EnumExtendedBeanPropertyRowMapper <T> extends BeanPropertyRowMapper<T> {
    public EnumExtendedBeanPropertyRowMapper(Class<T> class1) {
        super(class1);
    }
    
    @Override
    protected void initBeanWrapper(BeanWrapper bw) {
    	bw.registerCustomEditor(PositionEnum.class, new PositionEnumEditor());
    }
}
