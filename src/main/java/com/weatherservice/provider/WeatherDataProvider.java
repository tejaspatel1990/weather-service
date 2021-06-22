package com.weatherservice.provider;

import com.weatherservice.model.WeatherData;

/**
 * Provider definition for third partys weather data provider
 * 
 * @author tejas
 *
 */
public interface WeatherDataProvider {

	/**
	 * Get current weather data from an external provider
	 * 
	 * @param location
	 * @return
	 */
	public WeatherData getCurrentWeatherData(String location);
}
