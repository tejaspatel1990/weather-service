package com.weatherservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.weatherservice.apicore.exception.NoDataFoundException;
import com.weatherservice.constant.PropertiesConstant;
import com.weatherservice.model.CurrentWeatherData;
import com.weatherservice.model.WeatherHistory;
import com.weatherservice.repository.WeatherDataCustomRepository;
import com.weatherservice.repository.WeatherDataRepository;
import com.weatherservice.service.WeatherDataService;
import com.weatherservice.util.WeatherDataStatistics;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

	@Autowired
	private WeatherDataManager weatherDataManager;

	@Autowired
	private WeatherDataRepository weatherDataRepository;

	@Autowired
	private WeatherDataCustomRepository weatherDataCustomRepository;

	@Override
	public CurrentWeatherData getCurrentWeatherData(String location) {

		CurrentWeatherData weatherData = weatherDataManager.getCurrentWeatherData(location);
		saveWeatherData(weatherData);
		return weatherData;
	}

	@Override
	public WeatherHistory getWeatherHistory(String location) {

		CurrentWeatherData weatherData = new CurrentWeatherData();

		List<String> convertedLocation = Stream.of(location.split(",", 2)).collect(Collectors.toList());

		weatherData.setCityName(convertedLocation.get(0));

		if (convertedLocation.size() > 1 && convertedLocation.get(1) != null && !convertedLocation.get(1).isEmpty()) {
			weatherData.setCountry(convertedLocation.get(1));
		}

		List<CurrentWeatherData> weatherList = weatherDataCustomRepository.getWeatherHistory(weatherData, 5);

		if (weatherList == null || weatherList.isEmpty()) {
			throw new NoDataFoundException(PropertiesConstant.WEATHER_HOSTRY_NOT_FOUND);
		}

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

	@Async
	private void saveWeatherData(CurrentWeatherData weatherData) {
		weatherDataRepository.save(weatherData);
	}
}
