package com.weatherservice.constant;

/**
 * Constant class for Properties and messages defined
 * 
 * @author tejas
 *
 */
public final class PropertiesConstant {

	private PropertiesConstant() {
	}

	public final static String OWP_NOT_FOUND = "owp.404";
	public final static String OWP_UNAUTHORISED = "owp.401";
	public final static String OWP_TOO_MANY_REQUEST = "owp.429";
	public final static String PARAMETER_REQUIRED = "MissingServletRequestParameterException";
	public final static String INTERNAL_SERVER_EXCEPTION = "internal.server.error";
	public final static String WEATHER_HOSTRY_NOT_FOUND = "weather.history.not.found";
}
