package com.justa.springboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:noConfig.properties") 
public class NoConfig {

}
