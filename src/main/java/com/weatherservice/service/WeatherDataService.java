package com.weatherservice.service;

import java.util.List;

import com.weatherservice.apicore.exception.ApplicationErrorException;
import com.weatherservice.apicore.exception.NoDataFoundException;
import com.weatherservice.model.WeatherData;
import com.weatherservice.model.WeatherHistory;

public interface WeatherDataService {

	public WeatherData getCurrentWeatherData(String location) throws NoDataFoundException, ApplicationErrorException;

	public WeatherHistory getWeatherHistory(String location, Integer limit);

	public List<WeatherData> getListOfWeatherData(WeatherData weatherData, int limit);

}
