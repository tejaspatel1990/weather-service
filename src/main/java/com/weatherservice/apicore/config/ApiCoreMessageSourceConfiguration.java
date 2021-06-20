package com.weatherservice.apicore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.weatherservice.apicore.util.ApiCoreLocaleUtil;
import com.weatherservice.apicore.util.ApiCorei18nMessageUtil;

/**
 * Custom Message Source Configurations.
 * 
 * @author tejas
 *
 */
@Configuration
public class ApiCoreMessageSourceConfiguration {

	@Value("${i18n.message.path:locales/messages}")
	public String i18nMessagePath;

	/**
	 * Create Bean for Custom MessageSource.
	 * 
	 * @return
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:" + i18nMessagePath);
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public ApiCoreLocaleUtil apiCoreLocaleUtil() {
		return new ApiCoreLocaleUtil();
	}

	/**
	 * Create Bean of ApiCorei18nMessageUtil using custom message source and
	 * apiCoreLocalUtil.
	 * 
	 * @return
	 */
	@Bean
	public ApiCorei18nMessageUtil apiCorei18nMessageUtil() {
		return new ApiCorei18nMessageUtil(messageSource(), apiCoreLocaleUtil());
	}

}
