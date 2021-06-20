package com.weatherservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.weatherservice.apicore.exception.ApplicationErrorException;
import com.weatherservice.apicore.exception.NoDataFoundException;
import com.weatherservice.constant.PropertiesConstant;
import com.weatherservice.exception.CircuitBreakerFallBackException;
import com.weatherservice.model.WeatherData;
import com.weatherservice.provider.WeatherDataProvider;

@Service
public class WeatherDataManager {

	@Autowired
	@Qualifier("openWeatherMapProvider")
	private WeatherDataProvider openWeatherMapProvider;

	@Autowired
	@Qualifier("internalWeatherProvider")
	private WeatherDataProvider internalWeatherProvider;

	public WeatherData getCurrentWeatherData(String location) {

		try {
			return openWeatherMapProvider.getCurrentWeatherData(location);
		} catch (HttpClientErrorException exception) {
			if (exception.getStatusCode() == HttpStatus.UNAUTHORIZED) {

				throw new ApplicationErrorException(PropertiesConstant.OWP_UNAUTHORISED);

			} else if (exception.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
				throw new ApplicationErrorException(PropertiesConstant.OWP_TOO_MANY_REQUEST);

			} else if (exception.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new NoDataFoundException(PropertiesConstant.OWP_NOT_FOUND);
			}
		} catch (CircuitBreakerFallBackException exception) {
			return internalWeatherProvider.getCurrentWeatherData(location);
		}

		return null;
	}
}
