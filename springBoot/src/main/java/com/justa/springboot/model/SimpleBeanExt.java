package com.justa.springboot.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("SimpleBeanExt")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
// define this bean is created as singleton
public class SimpleBeanExt extends SimpleBean {

}
