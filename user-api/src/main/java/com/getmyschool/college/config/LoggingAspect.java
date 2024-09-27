package com.getmyschool.college.config;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.getmyschool.common.service.LoggingService;

@Aspect
@Component
public class LoggingAspect {

	private static Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	@Resource(name = "LoggingServiceImpl")
	private LoggingService loggingService;

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;

	private ThreadLocal<StringBuilder> threadLocal = new ThreadLocal<StringBuilder>();

	private StringBuilder builder;

	@Pointcut("execution (* com.getmyschool.college.controller.UserController.*(..))")
	public void loggingPointCut() {
	}

	@Before("loggingPointCut()")
	public void logBefore(JoinPoint joinPoint) throws Throwable {

		Object[] signatureArgs = joinPoint.getArgs();
		for (Object object : signatureArgs) {
			threadLocal.set(loggingService.displayReq(request, object));
			break;
		}
	}

	@AfterReturning(value = "execution (* com.getmyschool.college.controller.UserController.*(..))", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) throws Throwable {
		Object[] signatureArgs = joinPoint.getArgs();
		builder = new StringBuilder();
		builder.append(threadLocal.get());
		for (Object object : signatureArgs) {
			builder.append(loggingService.displayResp(request, response, object));
			break;
		}

		LOGGER.info(builder.toString());
		threadLocal.remove();
	}

	@AfterThrowing(value = "execution (* com.getmyschool.college.controller.UserController.*(..))", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Exception e) {
		builder = new StringBuilder();
		builder.append(threadLocal.get());
		LOGGER.error("Errror after throwing method:: " + builder.toString());
		threadLocal.remove();

		LOGGER.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
	}

}