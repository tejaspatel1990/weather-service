package com.weatherservice.repository;

import java.util.List;

import com.weatherservice.model.CurrentWeatherData;

public interface WeatherDataRepository {
	
	List<CurrentWeatherData> findWeatherDataByCity(String location , int limit);

}
