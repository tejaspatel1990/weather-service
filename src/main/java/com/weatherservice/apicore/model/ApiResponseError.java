package com.weatherservice.apicore.model;

import java.util.List;
import java.util.Map;

/**
 * Common API Response Error Structure
 * 
 * @author tejas
 *
 */
public class ApiResponseError {

	private String code;

	private String message;

	private List<Map<String, Object>> detail;

	public ApiResponseError(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ApiResponseError(String code, String message, List<Map<String, Object>> detail) {
		super();
		this.code = code;
		this.message = message;
		this.detail = detail;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public List<Map<String, Object>> getDetail() {
		return detail;
	}

}
