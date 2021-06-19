package com.weatherservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weatherservice.model.CurrentWeatherData;
import com.weatherservice.model.WeatherHistory;
import com.weatherservice.repository.WeatherDataRepository;
import com.weatherservice.service.WeatherDataService;
import com.weatherservice.util.WeatherDataStatistics;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

	@Autowired
	private WeatherDataManager weatherDataManager;

	@Autowired
	private WeatherDataRepository weatherDataRepository;

	@Override
	public CurrentWeatherData getCurrentWeatherData(String location) {

		return weatherDataManager.getCurrentWeatherData(location);
	}

	@Override
	public WeatherHistory getWeatherHistory(String location) {
		List<CurrentWeatherData> weatherList = weatherDataRepository.findWeatherDataByCity(location, 5);

		return computeAndBuildWeatherHistory(weatherList);
	}

	private WeatherHistory computeAndBuildWeatherHistory(List<CurrentWeatherData> weatherList) {
		WeatherHistory weatherHistory = new WeatherHistory();

		WeatherDataStatistics statistics = weatherList.stream().collect(WeatherDataStatistics::new,
				WeatherDataStatistics::accept, WeatherDataStatistics::combine);

		weatherHistory.setAveragePressure(statistics.getAveragePressure());
		weatherHistory.setAverageTemparature(statistics.getAverageTemparature());
		weatherHistory.setHistory(weatherList);
		return weatherHistory;
	}
}
