package com.weatherservice.repository;

import java.util.List;

import com.weatherservice.model.WeatherData;

public interface WeatherDataCustomRepository {

	public List<WeatherData> getWeatherHistory(WeatherData weatherData, int limit);
}
