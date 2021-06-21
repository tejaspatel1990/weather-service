package com.weatherservice.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.weatherservice.model.WeatherData;

public class WeatherDataUtilsTest {

	@Test
	public void getWeatherDataFilterFromLocationWithCityName() {
		String location = "Berlin";
		WeatherData weatherData = WeatherDataUtils.getWeatherDataFilterFromLocation(location);

		assertEquals("Berlin", weatherData.getCityName());
		assertNull(weatherData.getCountry());

	}

	@Test
	public void getWeatherDataFilterFromLocationWithCityAndCountry() {
		String location = "Berlin,De";
		WeatherData weatherData = WeatherDataUtils.getWeatherDataFilterFromLocation(location);

		assertEquals("Berlin", weatherData.getCityName());
		assertEquals("De", weatherData.getCountry());

	}

	@Test(expected = NullPointerException.class)
	public void getWeatherDataFilterFromLocationWithNullValues() {
		String location = null;
		WeatherData weatherData = WeatherDataUtils.getWeatherDataFilterFromLocation(location);

	}

	@Test()
	public void getWeatherDataFilterFromLocationWithBlankValue() {
		String location = "";
		WeatherData weatherData = WeatherDataUtils.getWeatherDataFilterFromLocation(location);

		assertEquals("", weatherData.getCityName());
		assertNull(weatherData.getCountry());

	}
}
