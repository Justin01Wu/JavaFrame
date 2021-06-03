package com.justa.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:service.properties") 
// this is global, will affect the whole application
// will load compliance.user.list from application firstly, then load from service.properties if didn't find it 
@ConfigurationProperties(prefix="application")
public class AppConfig {
	
	// in this way, you can centralize config
	private String name;
	private String scope;
	private Integer range;
	
	public String getName() {
		return name;
	}
	
	// spring boot automatically inject those values from config files with application.name
	public void setName(String name) {  
		this.name = name;
	}
	public String getScope() {
		return scope;
	}
	
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	public Integer getRange() {
		return range;
	}
	public void setRange(Integer range) {
		this.range = range;
	}

}
