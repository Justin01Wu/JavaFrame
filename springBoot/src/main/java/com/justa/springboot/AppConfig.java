package com.justa.springboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:service.properties") 
// this is global, will affect the whole application
// will load compliance.user.list from application firstly, then load from service.properties if didn't find it 
public class AppConfig {

}
