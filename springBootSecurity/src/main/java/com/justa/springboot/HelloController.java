package com.justa.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// from https://spring.io/guides/gs/serving-web-content/
@RestController
public class HelloController {
	
  @RequestMapping("/api/greeting")
  public String greeting() {
    return "Greetings from Spring Boot!";
  }
  
  @RequestMapping("/api/greeting2")
  //@ResponseBody
  public Greeting greeting2() {
    return new Greeting(1, "greeting2");
 
  }
  
  //@RequestMapping(value="/greeting3", produces={"application/json","application/xml"})  
  @RequestMapping(value="/api/greeting3", produces={"application/json"})
  // have to use produces to set return content-type, but it can be dynamically to match request content-type if it is a list  
  public String greeting3() {
	  
	  return "{\"msg\": \"greeting 3\"}";
 
  }

}
