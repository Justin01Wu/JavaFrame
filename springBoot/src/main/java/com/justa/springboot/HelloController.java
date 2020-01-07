package com.justa.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

// from https://spring.io/guides/gs/serving-web-content/
@RestController
public class HelloController {

  @RequestMapping("/greeting")
  public String greeting() {
    return "Greetings from Spring Boot!";
  }
  
  @RequestMapping("/greeting2")
  public Greeting greeting2() {
    return new Greeting(1, "greeting2");
 
  }

}
