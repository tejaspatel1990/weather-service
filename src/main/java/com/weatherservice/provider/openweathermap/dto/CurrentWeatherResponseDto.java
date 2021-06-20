package com.weatherservice.provider.openweathermap.dto;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeatherResponseDto {

	private String name;
	private List<WeatherDto> weather = new ArrayList<>();
	private Main main;

	private System sys;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public System getSys() {
		return sys;
	}

	public void setSys(System sys) {
		this.sys = sys;
	}

}
