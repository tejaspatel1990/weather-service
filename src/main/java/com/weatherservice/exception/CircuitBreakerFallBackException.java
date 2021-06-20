package com.weatherservice.exception;

import com.weatherservice.apicore.exception.ApplicationErrorException;

/**
 * Exception class for throwing error for fall back of circuit breaker
 * 
 * @author tejas
 *
 */
public class CircuitBreakerFallBackException extends ApplicationErrorException {

	public CircuitBreakerFallBackException(String key, String[] arguments) {
		super(key, arguments);
		// TODO Auto-generated constructor stub
	}

}
