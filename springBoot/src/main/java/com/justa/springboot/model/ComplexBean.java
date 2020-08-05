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
	
	public ComplexBean(
			@Qualifier("SimpleBean") SimpleBean simpleBean1
			// without @Qualifier it will fail because we have two types of SimpleBean: SimpleBean and SimpleBeanExt		
			) {		
		log.info("stage1 : calling the constructor with " + simpleBean1.getClass().getSimpleName());
		this.simpleBean1 = simpleBean1;
	}


	private static final Logger log = LoggerFactory.getLogger(ComplexBean.class);
	
	private SimpleBean simpleBean1;
	

	private SimpleBeanExt simpleBean2;

	@Autowired	
	public void setSimpleBean2(SimpleBeanExt simpleBean2) {
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


	public SimpleBean getSimpleBean1() {
		return simpleBean1;
	}


	public void setSimpleBean1(SimpleBean simpleBean1) {
		this.simpleBean1 = simpleBean1;
	}


	public SimpleBeanExt getSimpleBean2() {
		return simpleBean2;
	}


}
