package com.weatherservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.weatherservice.apicore.exception.NoDataFoundException;
import com.weatherservice.constant.PropertiesConstant;
import com.weatherservice.model.WeatherData;
import com.weatherservice.model.WeatherHistory;
import com.weatherservice.repository.WeatherDataCustomRepository;
import com.weatherservice.repository.WeatherDataRepository;
import com.weatherservice.service.WeatherDataService;
import com.weatherservice.util.WeatherDataStatistics;
import com.weatherservice.util.WeatherDataUtils;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

	@Value("${weather.history.defaul.limit}")
	private int limit;

	@Autowired
	private WeatherDataManager weatherDataManager;

	@Autowired
	private WeatherDataRepository weatherDataRepository;

	@Autowired
	private WeatherDataCustomRepository weatherDataCustomRepository;

	@Override
	public WeatherData getCurrentWeatherData(String location) {

		WeatherData weatherData = weatherDataManager.getCurrentWeatherData(location);
		saveWeatherData(weatherData);
		return weatherData;
	}

	@Override
	public WeatherHistory getWeatherHistory(String location, Integer limit) {

		// Setting up default limit
		if (limit == null || limit <= 0) {
			limit = this.limit;
		}
		WeatherData weatherData = WeatherDataUtils.getWeatherDataFilterFromLocation(location);

		List<WeatherData> weatherList = getListOfWeatherData(weatherData, limit);

		return computeAndBuildWeatherHistory(weatherList);
	}

	@Override
	public List<WeatherData> getListOfWeatherData(WeatherData weatherData, int limit) {
		List<WeatherData> weatherList = weatherDataCustomRepository.getWeatherHistory(weatherData, limit);

		if (weatherList == null || weatherList.isEmpty()) {
			throw new NoDataFoundException(PropertiesConstant.WEATHER_HOSTRY_NOT_FOUND);
		}
		return weatherList;
	}

	/**
	 * Create weather data statistics
	 * 
	 * @param weatherList
	 * @return
	 */
	private WeatherHistory computeAndBuildWeatherHistory(List<WeatherData> weatherList) {
		WeatherHistory weatherHistory = new WeatherHistory();

		WeatherDataStatistics statistics = weatherList.stream().collect(WeatherDataStatistics::new,
				WeatherDataStatistics::accept, WeatherDataStatistics::combine);

		weatherHistory.setAveragePressure(statistics.getAveragePressure());
		weatherHistory.setAverageTemparature(statistics.getAverageTemparature());
		weatherHistory.setHistory(weatherList);
		return weatherHistory;
	}

	/**
	 * Save current weather data
	 * 
	 * @param weatherData
	 */
	@Async
	private void saveWeatherData(WeatherData weatherData) {
		weatherDataRepository.save(weatherData);
	}

}
