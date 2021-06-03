package com.justa.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

// from https://spring.io/guides/gs/spring-boot/

@SpringBootApplication(  // it combines @Configuration, @ComponentScan and @EnableAutoConfiguration 
		scanBasePackages = {"com.justa.springboot"}
		//,exclude = {NoConfig.class}  // try to exclude, but failed, TODO: fix it
		// https://www.baeldung.com/spring-data-disable-auto-config   
		// can replace with spring.autoconfigure.exclude in config file
		)
@EnableScheduling
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {

		logger.info("    ==>> application started from here...");

		SpringApplication.run(Application.class, args);

	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			logger.info("Let's inspect the beans provided by Spring Boot:");

//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}

		};
	}

}
