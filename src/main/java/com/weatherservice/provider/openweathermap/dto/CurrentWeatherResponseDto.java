package com.weatherservice.provider.openweathermap.dto;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeatherResponseDto {

	private List<WeatherDto> weather = new ArrayList<>();
	private Main main;

	public List<WeatherDto> getWeather() {
		return weather;
	}

	public void setWeather(List<WeatherDto> weather) {
		this.weather = weather;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

}
