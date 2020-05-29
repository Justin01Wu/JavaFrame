package com.justa.springboot.model;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ComplexBean {
	
	public ComplexBean(SimpleBeanExt simpleBean1) {		
		super();
		log.info("stage1 : calling the constructor");
		this.simpleBean1 = simpleBean1;
	}


	private static final Logger log = LoggerFactory.getLogger(ComplexBean.class);
	
	private SimpleBeanExt simpleBean1;
	

	private SimpleBean simpleBean2;

	@Autowired
	@Qualifier("SimpleBean")  
	// without @Qualifier it will fail because we have two types of SimpleBean: SimpleBean and SimpleBeanExt
	public void setSimpleBean2(SimpleBean simpleBean2) {
		log.info("stage2 : calling the setter: " + simpleBean2.getClass().getSimpleName());
		this.simpleBean2 = simpleBean2;
	}

	
	@PostConstruct
	public void init() {
	   log.info("stage3 : calling the initMethod");
	}
	
	
	@PreDestroy
	public void destroy() {
	   log.info("stage4 : calling the destroy method");
	}


}
