package com.weatherservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weatherservice.model.CurrentWeatherData;
import com.weatherservice.model.WeatherHistory;
import com.weatherservice.service.WeatherDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Operation for weather data", description = "APIs for Weather Data")
@RestController
@RequestMapping(path = "api/v1/weather")
public class WeatherDataController {

	@Autowired
	private WeatherDataService weatherDataService;

	@ApiOperation(value = "Get current weather condition")
	@GetMapping("/current")
	public CurrentWeatherData getCurrentWeatherCondition(
			@RequestParam(name = "location", required = true) String location) {
		return weatherDataService.getCurrentWeatherData(location);
	}

	@ApiOperation(value = "Get historial weather data")
	@GetMapping("/history")
	public WeatherHistory getCurrentWeatherHistory(@RequestParam(name = "location", required = true) String location) {
		return weatherDataService.getWeatherHistory(location);
	}
}
