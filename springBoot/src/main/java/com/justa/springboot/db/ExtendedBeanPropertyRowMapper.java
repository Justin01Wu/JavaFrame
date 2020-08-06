package com.justa.springboot.db;

import org.springframework.beans.BeanWrapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class ExtendedBeanPropertyRowMapper <T> extends BeanPropertyRowMapper<T> {
    public ExtendedBeanPropertyRowMapper(Class<T> class1) {
        super(class1);
    }
    
    @Override
    protected void initBeanWrapper(BeanWrapper bw) {
    	//bw.
    }
}
