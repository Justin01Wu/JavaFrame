package com.justa.springboot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserRepoMonitor {
	private static final Logger logger = LoggerFactory.getLogger(UserRepoMonitor.class);

	@Before("execution(* com.justa.springboot.db.*Repository.getUsersByNameAndPosition(..))")
	// first * means any return type, second * means any class end with Repository
	public void beforeFindById(JoinPoint joinPoint) throws Throwable {
		
		Signature s = joinPoint.getSignature();
		String methodName = s.getName();
		logger.info(" ---> Method " + methodName + " is about to be called");
		
		String kind = joinPoint.getKind();
		logger.info(" ---> kind =" + kind );
		
		Class<?> c = s.getDeclaringType();
		logger.info(" ---> original class =" + c.getName() );
		
		c = s.getClass();
		logger.info(" ---> signuture class =" + c.getName() );
		
		String name = s.getName();
		logger.info(" ---> signuture name =" + name );
		
		String ls = s.toLongString();
		logger.info(" ---> signuture  =" + ls );
		
		Object[] objs = joinPoint.getArgs();
		for(Object obj :objs) {
			logger.info(" ---> arg  =" + obj);
		}
		
		

	}
}
