package com.weatherservice.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.weatherservice.model.WeatherData;

public class WeatherDataUtils {

	/**
	 * Get weather data object used as filter from Location
	 * 
	 * @param location
	 * @return
	 */
	public static WeatherData getWeatherDataFilterFromLocation(String location) {
		WeatherData weatherData = new WeatherData();

		List<String> convertedLocation = Stream.of(location.split(",", 2)).collect(Collectors.toList());

		weatherData.setCityName(convertedLocation.get(0));

		if (convertedLocation.size() > 1 && convertedLocation.get(1) != null && !convertedLocation.get(1).isEmpty()) {
			weatherData.setCountry(convertedLocation.get(1));
		}

		return weatherData;
	}

}
