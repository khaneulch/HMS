package com.hms.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerAspect {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	String name = null;
	String type = null;

	@Around("execution(* com..*Controller.*(..)) or execution(* com..*Impl.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		type = joinPoint.getSignature().getDeclaringTypeName();
		
		if(type.indexOf("Controller") > -1) {
			name = "Controller \t: ";
		}
		else if(type.indexOf("Service") > -1) {
			name = "Service \t: ";
		}
		else if(type.indexOf("DAO") > -1) {
			name = "DAO \t\t: ";
		}
		logger.debug(name + type + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}
}
