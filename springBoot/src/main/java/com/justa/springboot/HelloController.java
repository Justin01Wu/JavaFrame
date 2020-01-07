package com.justa.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

// from https://spring.io/guides/gs/serving-web-content/
@RestController
public class HelloController {

  @RequestMapping("/greeting")
  public String index() {
    return "Greetings from Spring Boot!";
  }

}
