package com.fleetflow.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLoggingAspect {

	private static final Logger LOGGER = Logger.getLogger(ServiceLoggingAspect.class.getName());

	@Around("execution(public * com.fleetflow.service..*(..))")
	public Object logServiceCall(ProceedingJoinPoint joinPoint) throws Throwable {
		long startedAt = System.nanoTime();
		try {
			return joinPoint.proceed();
		}
		catch (Throwable error) {
			LOGGER.log(Level.WARNING, "Service call failed: " + joinPoint.getSignature().toShortString(), error);
			throw error;
		}
		finally {
			long elapsedMillis = (System.nanoTime() - startedAt) / 1_000_000;
			LOGGER.fine(() -> "Service call completed: " + joinPoint.getSignature().toShortString()
					+ " (" + elapsedMillis + " ms)");
		}
	}
}
