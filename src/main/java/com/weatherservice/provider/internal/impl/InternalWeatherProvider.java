package com.weatherservice.provider.internal.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.weatherservice.model.WeatherData;
import com.weatherservice.provider.WeatherDataProvider;
import com.weatherservice.service.WeatherDataService;
import com.weatherservice.util.WeatherDataUtils;

@Service("internalWeatherProvider")
public class InternalWeatherProvider implements WeatherDataProvider {

	@Autowired
	@Lazy
	private WeatherDataService weatherDataService;

	@Override
	public WeatherData getCurrentWeatherData(String location) {
		WeatherData weatherData = WeatherDataUtils.getWeatherDataFilterFromLocation(location);
		List<WeatherData> weatherDatas = weatherDataService.getListOfWeatherData(weatherData, 1);
		return weatherDatas.get(0);
	}

}
