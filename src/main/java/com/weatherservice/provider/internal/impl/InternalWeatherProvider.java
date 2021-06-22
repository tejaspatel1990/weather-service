package com.weatherservice.provider.internal.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.weatherservice.model.WeatherData;
import com.weatherservice.provider.WeatherDataProvider;
import com.weatherservice.service.WeatherDataService;
import com.weatherservice.util.WeatherDataUtils;

/**
 * Internal Weather Provider
 * 
 * @author tejas
 *
 */
@Service("internalWeatherProvider")
public class InternalWeatherProvider implements WeatherDataProvider {

	private static final Logger LOG = LoggerFactory.getLogger(InternalWeatherProvider.class);

	@Autowired
	@Lazy
	private WeatherDataService weatherDataService;

	@Override
	public WeatherData getCurrentWeatherData(String location) {

		LOG.info("Getting current weather data from internal provider for location {}", location);

		WeatherData weatherData = WeatherDataUtils.getWeatherDataFilterFromLocation(location);
		List<WeatherData> weatherDatas = weatherDataService.getListOfWeatherData(weatherData, 1);
		return weatherDatas.get(0);
	}

}
