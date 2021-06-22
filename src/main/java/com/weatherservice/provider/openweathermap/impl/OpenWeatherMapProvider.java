package com.weatherservice.provider.openweathermap.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.weatherservice.constant.PropertiesConstant;
import com.weatherservice.exception.CircuitBreakerFallBackException;
import com.weatherservice.model.WeatherData;
import com.weatherservice.provider.WeatherDataProvider;
import com.weatherservice.provider.openweathermap.dto.CurrentWeatherResponseDto;
import com.weatherservice.provider.openweathermap.util.OpenWeatherMapUtils;

/**
 * Open weather Map Provider class
 * 
 * @author tejas
 *
 */
@Service("openWeatherMapProvider")
public class OpenWeatherMapProvider implements WeatherDataProvider {

	private static final Logger LOG = LoggerFactory.getLogger(OpenWeatherMapProvider.class);

	@Value("${openweathermap.apikey}")
	private String apiKey;

	@Value("${openweathermap.current.weather.url}")
	private String currentWeatherUrl;

	@Value("${openweathermap.current.weather.umbrella.conditions}")
	private String umbrellaCondition;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	@HystrixCommand(fallbackMethod = "getFallbackWeatherData")
	public WeatherData getCurrentWeatherData(String location) throws HttpClientErrorException {

		LOG.info("Calling open weather map api to fetch current weather of city {}", location);

		String url = buildCurrentWeatherURI(location);
		ResponseEntity<CurrentWeatherResponseDto> entity = restTemplate.getForEntity(url,
				CurrentWeatherResponseDto.class);
		return buildCurrentWeatherDataModel(entity.getBody());

	}

	public WeatherData getFallbackWeatherData(String location) {
		throw new CircuitBreakerFallBackException(PropertiesConstant.INTERNAL_SERVER_EXCEPTION, null);
	}

	private String buildCurrentWeatherURI(String location) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(currentWeatherUrl)
				.queryParam("q", location).queryParam("appid", apiKey).build();
		return uriComponents.toUriString();
	}

	private WeatherData buildCurrentWeatherDataModel(CurrentWeatherResponseDto responseDto) {
		WeatherData currentWeather = new WeatherData();
		currentWeather.setPressure(responseDto.getMain().getPressure());
		currentWeather.setTemparature(responseDto.getMain().getTemp());
		boolean takeUmbrella = OpenWeatherMapUtils.takeUmbrella(responseDto.getWeather().get(0).getMain(),
				umbrellaCondition);
		currentWeather.setUmbrella(takeUmbrella);
		currentWeather.setCityName(responseDto.getName());
		currentWeather.setCountry(responseDto.getSys().getCountry());

		return currentWeather;
	}

}
