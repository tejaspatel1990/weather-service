package com.weatherservice.apicore.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Locale Utility to get Locale from Context or Request
 * 
 * @author Yogeen Loriya
 *
 */
public class ApiCoreLocaleUtil {

	public Locale getLocale() {
		Locale locale = getHttpServletRequest().getLocale();
		if (locale == null) {
			locale = LocaleContextHolder.getLocale();
		}
		return locale;
	}

	/**
	 * Get HttpServletRequest from Context
	 * 
	 * @return
	 */
	public HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

}
