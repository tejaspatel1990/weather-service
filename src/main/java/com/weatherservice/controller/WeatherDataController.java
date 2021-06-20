package com.weatherservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weatherservice.apicore.model.ApiResponse;
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
	public ApiResponse<CurrentWeatherData> getCurrentWeatherCondition(
			@RequestParam(name = "location", required = true) String location) {
		CurrentWeatherData weatherData = weatherDataService.getCurrentWeatherData(location);
		ApiResponse<CurrentWeatherData> apiResponse = new ApiResponse<>(weatherData, HttpStatus.OK.value(), null);
		return apiResponse;
	}

	@ApiOperation(value = "Get historial weather data")
	@GetMapping("/history")
	public ApiResponse<WeatherHistory> getCurrentWeatherHistory(
			@RequestParam(name = "location", required = true) String location) {
		WeatherHistory weatherHistory = weatherDataService.getWeatherHistory(location);
		ApiResponse<WeatherHistory> apiResponse = new ApiResponse<>(weatherHistory, HttpStatus.OK.value(), null);
		return apiResponse;
	}
}
