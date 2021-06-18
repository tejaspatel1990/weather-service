package com.weatherservice.apicore.exception;

/**
 * Based exception class for throwing all custom exception
 * 
 * @author tejas
 *
 */
public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String key;

	private String[] arguments;

	public BaseException(String key, String... arguments) {
		super();
		this.key = key;
		this.arguments = arguments;
	}

	public String getKey() {
		return key;
	}

	public String[] getArguments() {
		return arguments;
	}
}
