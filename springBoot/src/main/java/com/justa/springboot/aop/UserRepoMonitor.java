package com.justa.springboot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserRepoMonitor {
	private static final Logger logger = LoggerFactory.getLogger(UserRepoMonitor.class);

	@Before("execution(* com.justa.springboot.db.*UserRepository.getUsersByNameAndPosition(..))")
	public void beforeFindById(JoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();
		logger.info(" ---> Method " + methodName + " is about to be called");
	}
}
