package com.weatherservice.apicore.exception;

/**
 * Custom Application Error Exception 
 * 
 * @author tejas
 *
 */
public class ApplicationErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String key;

	private String[] arguments;

	public ApplicationErrorException(String key, String... arguments) {
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
