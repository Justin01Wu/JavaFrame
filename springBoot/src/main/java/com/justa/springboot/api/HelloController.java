package com.justa.springboot.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justa.springboot.AppConfig;
import com.justa.springboot.model.ComplexBean;
import com.justa.springboot.model.Greeting;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

// from https://spring.io/guides/gs/serving-web-content/
@RestController
public class HelloController {
	
	private MeterRegistry meterRegistry;
	private Counter myCounter;
	
	@Autowired
	private ComplexBean bean;
	
	@Autowired
	private AppConfig config;
	
	@Autowired
	public HelloController(	MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
		
		myCounter = Counter    .builder("greeting3Counter")
		        .description("indicates called count of the greeting3 api")
		        .tags("dev", "performance")
		        .register(meterRegistry);
	}

  @RequestMapping("/api/greeting")
  public String greeting() {
    return "Greetings from Spring Boot! name=" + config.getName();
  }
  
  @RequestMapping("/api/greeting2")
  //@ResponseBody
  public Greeting greeting2() {
    return new Greeting(1, "greeting2", config.getRange());
 
  }
  
  //@RequestMapping(value="/api/greeting3", produces={"application/json","application/xml"})  
  @RequestMapping(value="/api/greeting3", produces={"application/json"})
  // have to use produces to set return content-type, but it can be dynamically to match request content-type if it is a list  
  public String greeting3() {
	  
	  // customized metrics for actuator
	  myCounter.increment();
	  meterRegistry.gauge("greeting3_timeStamp", new Date().getTime());
	  
	  return "{\"msg\": \"greeting 3\"}";
 
  }

}
