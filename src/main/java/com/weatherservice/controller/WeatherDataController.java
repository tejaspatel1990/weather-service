package com.weatherservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weatherservice.apicore.exception.ApplicationErrorException;
import com.weatherservice.apicore.exception.NoDataFoundException;
import com.weatherservice.apicore.model.ApiResponse;
import com.weatherservice.apicore.util.ApiCorei18nMessageUtil;
import com.weatherservice.model.WeatherData;
import com.weatherservice.model.WeatherHistory;
import com.weatherservice.service.WeatherDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Apis performing operation for weather data
 * 
 * @author tejas
 *
 */
@Api(value = "Operation for weather data", description = "APIs for Weather Data")
@RestController
@RequestMapping(path = "api/v1/weather")
public class WeatherDataController {

	private static final Logger LOG = LoggerFactory.getLogger(WeatherDataController.class);

	@Autowired
	private WeatherDataService weatherDataService;

	@ApiOperation(value = "Get current weather condition")
	@GetMapping("/current")
	public ApiResponse<WeatherData> getCurrentWeatherCondition(
			@RequestParam(name = "location", required = true) String location)
			throws NoDataFoundException, ApplicationErrorException {

		LOG.info("Getting current weather data for location {}", location);

		WeatherData weatherData = weatherDataService.getCurrentWeatherData(location);
		ApiResponse<WeatherData> apiResponse = new ApiResponse<>(weatherData, HttpStatus.OK.value(), null);

		LOG.info("Successfully fetched current weather data for condition {}", location);
		return apiResponse;
	}

	@ApiOperation(value = "Get historial weather data")
	@GetMapping("/history")
	public ApiResponse<WeatherHistory> getCurrentWeatherHistory(
			@RequestParam(name = "location", required = true) String location,
			@RequestParam(name = "limit", required = false) Integer limit) {

		LOG.info("Getting  weather data history for location {} and limit {}", location, limit);

		WeatherHistory weatherHistory = weatherDataService.getWeatherHistory(location, limit);
		ApiResponse<WeatherHistory> apiResponse = new ApiResponse<>(weatherHistory, HttpStatus.OK.value(), null);

		LOG.info("Successfully fetched  weather data history for location {} and limit {}", location, limit);

		return apiResponse;
	}
}
