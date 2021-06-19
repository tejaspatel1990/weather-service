package com.weatherservice.service;

import com.weatherservice.model.CurrentWeatherData;

public interface WeatherDataService {
	
	public CurrentWeatherData getCurrentWeatherData(String location);

}
