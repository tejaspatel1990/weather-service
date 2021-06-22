package com.weatherservice.apicore.model;

import java.util.List;

/**
 * Common API response structure
 * 
 * @author tejas
 *
 * @param <T>
 */
public class ApiResponse<T> {

	private T data;

	private Integer status;

	private String message;

	private List<ApiResponseError> errors;

	public ApiResponse() {
		super();
	}

	public ApiResponse(T data, Integer status, String message) {
		this(data, status, message, null);
	}

	public ApiResponse(Integer status, String message, List<ApiResponseError> errors) {
		this(null, status, message, errors);
	}

	public ApiResponse(T data, Integer status, String message, List<ApiResponseError> errors) {
		super();
		this.data = data;
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	public T getData() {
		return data;
	}

	public Integer getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public List<ApiResponseError> getErrors() {
		return errors;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setErrors(List<ApiResponseError> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "ApiResponse [data=" + data + ", status=" + status + ", message=" + message + "]";
	}

}
