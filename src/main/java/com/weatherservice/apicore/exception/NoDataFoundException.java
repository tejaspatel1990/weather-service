package com.weatherservice.apicore.exception;

/**
 * Exception class for No Data found
 * 
 * @author tejas
 *
 */
public class NoDataFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String key;

	private String[] arguments;

	public NoDataFoundException(String key, String... arguments) {
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
