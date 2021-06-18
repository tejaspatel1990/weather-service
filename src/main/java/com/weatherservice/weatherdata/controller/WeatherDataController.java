package com.weatherservice.weatherdata.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("APIs for Weather Data")
@RestController
@RequestMapping(path = "api/v1/weather")
public class WeatherDataController {

	@ApiOperation(value = "Get current weather condition")
	@GetMapping("/current")
	public String getCurrentWeatherCondition(@RequestParam(name = "location", required = true) String location) {
		return "Hellow world";
	}
}
