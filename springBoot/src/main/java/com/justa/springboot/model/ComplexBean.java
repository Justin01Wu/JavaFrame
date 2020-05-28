package com.justa.springboot.model;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComplexBean {
	
	public ComplexBean(SimpleBean simpleBean1) {		
		super();
		log.info("stage1 : calling the constructor");
		this.simpleBean1 = simpleBean1;
	}


	private static final Logger log = LoggerFactory.getLogger(ComplexBean.class);
	
	private SimpleBean simpleBean1;
	

	private SimpleBean simpleBean2;

	@Autowired
	public void setSimpleBean2(SimpleBean simpleBean2) {
		log.info("stage2 : calling the setter");
		this.simpleBean2 = simpleBean2;
	}

	
	@PostConstruct
	public void init() {
	   log.info("stage3 : calling the initMethod");
	}
	
	
	@PreDestroy
	public void destroy() {
	   log.info("stage4 : calling the initMethod");
	}


}
