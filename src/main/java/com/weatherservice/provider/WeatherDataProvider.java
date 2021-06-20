package com.weatherservice.provider;

import com.weatherservice.model.CurrentWeatherData;

/**
 * Provider definition for third partys weather data provider
 * 
 * @author tejas
 *
 */
public interface WeatherDataProvider {

	public CurrentWeatherData getCurrentWeatherData(String location);
}
