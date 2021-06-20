package com.weatherservice.provider;

import com.weatherservice.model.WeatherData;

/**
 * Provider definition for third partys weather data provider
 * 
 * @author tejas
 *
 */
public interface WeatherDataProvider {

	public WeatherData getCurrentWeatherData(String location);
}
