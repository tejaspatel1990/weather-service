package com.weatherservice.repository;

import java.util.List;

import com.weatherservice.model.CurrentWeatherData;

public interface WeatherDataCustomRepository {

	public List<CurrentWeatherData> getWeatherHistory(CurrentWeatherData weatherData, int limit);
}
