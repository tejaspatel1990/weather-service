package com.weatherservice.apicore.config;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weatherservice.apicore.exception.ApplicationErrorException;
import com.weatherservice.apicore.exception.NoDataFoundException;
import com.weatherservice.apicore.model.ApiResponse;
import com.weatherservice.apicore.model.ApiResponseError;
import com.weatherservice.apicore.util.ApiCorei18nMessageUtil;
import com.weatherservice.constant.PropertiesConstant;

/**
 * Global Exception Handler to response common API structure
 * 
 * @author tejas
 *
 */
@ControllerAdvice
@Order(2)
public class ApiCoreGlobalExceptionHandler {

	@Autowired
	private ApiCorei18nMessageUtil apiCorei18nMessageUtil;

	@ExceptionHandler({ NoDataFoundException.class, AccessDeniedException.class, ApplicationErrorException.class,
			RuntimeException.class, MissingServletRequestParameterException.class })
	@ResponseBody
	public final ResponseEntity<ApiResponse<Object>> handleException(Exception ex) throws Exception {
		if (ex instanceof NoDataFoundException) {
			return handleNoDataFoundException((NoDataFoundException) ex);
		} else if (ex instanceof MissingServletRequestParameterException) {
			return handleMissingServletRequestParameterException((MissingServletRequestParameterException) ex);
		} else if (ex instanceof AccessDeniedException) {
			return handleAccessDeniedException((AccessDeniedException) ex);
		} else if (ex instanceof ApplicationErrorException) {
			return handleApplicationErrorException((ApplicationErrorException) ex);
		} else {
			return handleUnknownException(ex);
		}

	}

	/**
	 * Handle Missing Servlet Request Parameter Exception
	 * 
	 * @param ex
	 * @return
	 */
	private ResponseEntity<ApiResponse<Object>> handleMissingServletRequestParameterException(
			final MissingServletRequestParameterException ex) {

		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ApiResponse<Object> body = new ApiResponse<>(httpStatus.value(), ex.getParameterName() + " "
				+ apiCorei18nMessageUtil.getLocalizeMessage(PropertiesConstant.PARAMETER_REQUIRED), null);
		return handleExceptionInternal(ex, body, null, httpStatus);
	}

	/**
	 * Handle No Data found Exceptions
	 * 
	 * @param ex
	 * @return
	 */
	private ResponseEntity<ApiResponse<Object>> handleNoDataFoundException(final NoDataFoundException ex) {
		HttpStatus httpStatus = HttpStatus.NOT_FOUND;

		List<ApiResponseError> errors = new ArrayList<>();
		ApiResponseError error = new ApiResponseError(ex.getKey(), null);
		errors.add(error);
		ApiResponse<Object> body = new ApiResponse<>(httpStatus.value(),
				apiCorei18nMessageUtil.getLocalizeMessage(ex.getKey(), ex.getArguments()), errors);

		return handleExceptionInternal(ex, body, null, httpStatus);
	}

	private ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(final AccessDeniedException ex) {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		ApiResponse<Object> body = new ApiResponse<>(httpStatus.value(), ex.getMessage(), null);
		return handleExceptionInternal(ex, body, null, httpStatus);
	}

	private ResponseEntity<ApiResponse<Object>> handleApplicationErrorException(final ApplicationErrorException ex) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		List<ApiResponseError> errors = new ArrayList<>();
		ApiResponseError error = new ApiResponseError(ex.getKey(), null);
		errors.add(error);
		ApiResponse<Object> body = new ApiResponse<>(httpStatus.value(),
				apiCorei18nMessageUtil.getLocalizeMessage(ex.getKey(), ex.getArguments()), errors);
		return handleExceptionInternal(ex, body, null, httpStatus);
	}

	private ResponseEntity<ApiResponse<Object>> handleUnknownException(Exception ex) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		List<ApiResponseError> errors = new ArrayList<>();
		ApiResponseError error = new ApiResponseError(null, ex.getLocalizedMessage());
		errors.add(error);
		ApiResponse<Object> body = new ApiResponse<>(httpStatus.value(),
				apiCorei18nMessageUtil.getLocalizeMessage(PropertiesConstant.INTERNAL_SERVER_EXCEPTION), errors);
		return handleExceptionInternal(ex, body, null, httpStatus);
	}

	private ResponseEntity<ApiResponse<Object>> handleExceptionInternal(Exception ex,
			@Nullable ApiResponse<Object> body, HttpHeaders headers, HttpStatus status) {
		return new ResponseEntity<>(body, headers, status);
	}

	public ApiCoreGlobalExceptionHandler withMessageUtil(ApiCorei18nMessageUtil apiCorei18nMessageUtil) {
		this.apiCorei18nMessageUtil = apiCorei18nMessageUtil;
		return this;
	}

}
