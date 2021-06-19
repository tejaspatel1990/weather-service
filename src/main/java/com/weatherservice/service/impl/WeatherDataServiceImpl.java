package com.weatherservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weatherservice.model.CurrentWeatherData;
import com.weatherservice.provider.WeatherDataProvider;
import com.weatherservice.service.WeatherDataService;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

	@Autowired
	private WeatherDataProvider weatherDataProvider; 
	
	@Override
	public CurrentWeatherData getCurrentWeatherData(String location) {
		
		return weatherDataProvider.getCurrentWeatherData(location);
	}

}
