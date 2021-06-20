package com.weatherservice.service;

import com.weatherservice.model.CurrentWeatherData;
import com.weatherservice.model.WeatherHistory;

public interface WeatherDataService {
	
	public CurrentWeatherData getCurrentWeatherData(String location);

	public WeatherHistory getWeatherHistory(String location);
	
}
