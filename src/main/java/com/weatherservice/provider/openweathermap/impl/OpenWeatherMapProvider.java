package com.weatherservice.provider.openweathermap.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.weatherservice.model.CurrentWeatherData;
import com.weatherservice.provider.WeatherDataProvider;
import com.weatherservice.provider.openweathermap.dto.CurrentWeatherResponseDto;
import com.weatherservice.provider.openweathermap.util.OpenWeatherMapUtils;

@Service("openWeatherMapProvider")
public class OpenWeatherMapProvider implements WeatherDataProvider {

	@Value("${openweathermap.apikey}")
	private String apiKey;

	@Value("${openweathermap.current.weather.url}")
	private String currentWeatherUrl;

	@Value("${openweathermap.current.weather.umbrella.conditions}")
	private String umbrellaCondition;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public CurrentWeatherData getCurrentWeatherData(String location) {

		ResponseEntity<CurrentWeatherResponseDto> entity = restTemplate.getForEntity(buildCurrentWeatherURI(location),
				CurrentWeatherResponseDto.class);

		return buildCurrentWeatherDataModel(entity.getBody());
	}

	private String buildCurrentWeatherURI(String location) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(currentWeatherUrl)
				.queryParam("q", location).queryParam("appid", apiKey).build();
		return uriComponents.toUriString();
	}

	private CurrentWeatherData buildCurrentWeatherDataModel(CurrentWeatherResponseDto responseDto) {
		CurrentWeatherData currentWeather = new CurrentWeatherData();
		currentWeather.setPressure(responseDto.getMain().getPressure());
		currentWeather.setTemparature(responseDto.getMain().getTemp());
		boolean takeUmbrella = OpenWeatherMapUtils.takeUmbrella(responseDto.getWeather().get(0).getMain(),
				umbrellaCondition);
		currentWeather.setUmbrella(takeUmbrella);

		return currentWeather;
	}

}
