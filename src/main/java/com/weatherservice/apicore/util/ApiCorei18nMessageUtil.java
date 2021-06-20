package com.weatherservice.apicore.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

/**
 * Utility class to get localize message
 * 
 * @author Yogeen Loriya
 *
 */
public class ApiCorei18nMessageUtil {

	private static final Logger LOG = LoggerFactory.getLogger(ApiCorei18nMessageUtil.class);

	private final MessageSource messageSource;

	private final ApiCoreLocaleUtil apiCoreLocaleUtil;

	public ApiCorei18nMessageUtil(MessageSource messageSource, ApiCoreLocaleUtil apiCoreLocaleUtil) {
		this.messageSource = messageSource;
		this.apiCoreLocaleUtil = apiCoreLocaleUtil;
	}

	/**
	 * Get Localize message
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public String getLocalizeMessage(String key, String... args) {
		String message = key;
		try {
			Locale locale = apiCoreLocaleUtil.getLocale();
			if (locale == null) {
				locale = Locale.getDefault();
			}
			message = this.messageSource.getMessage(key, args, locale);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return message;
	}

}
