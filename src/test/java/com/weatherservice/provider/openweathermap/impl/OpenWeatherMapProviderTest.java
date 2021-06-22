package com.weatherservice.provider.openweathermap.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.weatherservice.model.WeatherData;
import com.weatherservice.provider.openweathermap.dto.CurrentWeatherResponseDto;
import com.weatherservice.provider.openweathermap.dto.Main;
import com.weatherservice.provider.openweathermap.dto.System;
import com.weatherservice.provider.openweathermap.dto.WeatherDto;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.weatherservice.*")
public class OpenWeatherMapProviderTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private OpenWeatherMapProvider openWeatherMapProvider;

	@Before
	public void before() {
		ReflectionTestUtils.setField(openWeatherMapProvider, "apiKey", "123");
		ReflectionTestUtils.setField(openWeatherMapProvider, "currentWeatherUrl", "http/owp/");
		ReflectionTestUtils.setField(openWeatherMapProvider, "umbrellaCondition", "Thunderstorm,Drizzle,Rain");
	}

	@Test
	public void getCurrentWeatherDataSuccessfullyForThunderstorm() {

		String location = "Berlin";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(true);

		System system = new System();
		system.setCountry("De");

		Main main = new Main();
		main.setPressure(12.0f);
		main.setTemp(12.0f);

		WeatherDto dto = new WeatherDto();
		dto.setMain("Thunderstorm");

		List<WeatherDto> dtos = new ArrayList<>();
		dtos.add(dto);

		CurrentWeatherResponseDto currentWeatherResponseDto = new CurrentWeatherResponseDto();
		currentWeatherResponseDto.setMain(main);
		currentWeatherResponseDto.setSys(system);
		currentWeatherResponseDto.setWeather(dtos);
		currentWeatherResponseDto.setName(location);

		ResponseEntity<CurrentWeatherResponseDto> responseEntity = new ResponseEntity<CurrentWeatherResponseDto>(
				currentWeatherResponseDto, HttpStatus.OK);

		PowerMockito
				.when(restTemplate.getForEntity("http://http/owp/?q=Berlin&appid=123", CurrentWeatherResponseDto.class))
				.thenReturn(responseEntity);

		WeatherData acWeatherData = openWeatherMapProvider.getCurrentWeatherData(location);

		assertNotNull(acWeatherData);
		assertEquals(weatherData.getCityName(), acWeatherData.getCityName());
		assertEquals(weatherData.getPressure(), acWeatherData.getPressure());
		assertEquals(weatherData.isUmbrella(), acWeatherData.isUmbrella());
	}
	
	@Test
	public void getCurrentWeatherDataSuccessfullyForRain() {

		String location = "Berlin";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(true);

		System system = new System();
		system.setCountry("De");

		Main main = new Main();
		main.setPressure(12.0f);
		main.setTemp(12.0f);

		WeatherDto dto = new WeatherDto();
		dto.setMain("Rain");

		List<WeatherDto> dtos = new ArrayList<>();
		dtos.add(dto);

		CurrentWeatherResponseDto currentWeatherResponseDto = new CurrentWeatherResponseDto();
		currentWeatherResponseDto.setMain(main);
		currentWeatherResponseDto.setSys(system);
		currentWeatherResponseDto.setWeather(dtos);
		currentWeatherResponseDto.setName(location);

		ResponseEntity<CurrentWeatherResponseDto> responseEntity = new ResponseEntity<CurrentWeatherResponseDto>(
				currentWeatherResponseDto, HttpStatus.OK);

		PowerMockito
				.when(restTemplate.getForEntity("http://http/owp/?q=Berlin&appid=123", CurrentWeatherResponseDto.class))
				.thenReturn(responseEntity);

		WeatherData acWeatherData = openWeatherMapProvider.getCurrentWeatherData(location);

		assertNotNull(acWeatherData);
		assertEquals(weatherData.getCityName(), acWeatherData.getCityName());
		assertEquals(weatherData.getPressure(), acWeatherData.getPressure());
		assertEquals(weatherData.isUmbrella(), acWeatherData.isUmbrella());
	}
	
	@Test
	public void getCurrentWeatherDataSuccessfullyForDrizzle() {

		String location = "Berlin";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(true);

		System system = new System();
		system.setCountry("De");

		Main main = new Main();
		main.setPressure(12.0f);
		main.setTemp(12.0f);

		WeatherDto dto = new WeatherDto();
		dto.setMain("Drizzle");

		List<WeatherDto> dtos = new ArrayList<>();
		dtos.add(dto);

		CurrentWeatherResponseDto currentWeatherResponseDto = new CurrentWeatherResponseDto();
		currentWeatherResponseDto.setMain(main);
		currentWeatherResponseDto.setSys(system);
		currentWeatherResponseDto.setWeather(dtos);
		currentWeatherResponseDto.setName(location);

		ResponseEntity<CurrentWeatherResponseDto> responseEntity = new ResponseEntity<CurrentWeatherResponseDto>(
				currentWeatherResponseDto, HttpStatus.OK);

		PowerMockito
				.when(restTemplate.getForEntity("http://http/owp/?q=Berlin&appid=123", CurrentWeatherResponseDto.class))
				.thenReturn(responseEntity);

		WeatherData acWeatherData = openWeatherMapProvider.getCurrentWeatherData(location);

		assertNotNull(acWeatherData);
		assertEquals(weatherData.getCityName(), acWeatherData.getCityName());
		assertEquals(weatherData.getPressure(), acWeatherData.getPressure());
		assertEquals(weatherData.isUmbrella(), acWeatherData.isUmbrella());
	}
	
	@Test
	public void getCurrentWeatherDataSuccessfullyForCloud() {

		String location = "Berlin";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		System system = new System();
		system.setCountry("De");

		Main main = new Main();
		main.setPressure(12.0f);
		main.setTemp(12.0f);

		WeatherDto dto = new WeatherDto();
		dto.setMain("Cloud");

		List<WeatherDto> dtos = new ArrayList<>();
		dtos.add(dto);

		CurrentWeatherResponseDto currentWeatherResponseDto = new CurrentWeatherResponseDto();
		currentWeatherResponseDto.setMain(main);
		currentWeatherResponseDto.setSys(system);
		currentWeatherResponseDto.setWeather(dtos);
		currentWeatherResponseDto.setName(location);

		ResponseEntity<CurrentWeatherResponseDto> responseEntity = new ResponseEntity<CurrentWeatherResponseDto>(
				currentWeatherResponseDto, HttpStatus.OK);

		PowerMockito
				.when(restTemplate.getForEntity("http://http/owp/?q=Berlin&appid=123", CurrentWeatherResponseDto.class))
				.thenReturn(responseEntity);

		WeatherData acWeatherData = openWeatherMapProvider.getCurrentWeatherData(location);

		assertNotNull(acWeatherData);
		assertEquals(weatherData.getCityName(), acWeatherData.getCityName());
		assertEquals(weatherData.getPressure(), acWeatherData.getPressure());
		assertEquals(weatherData.isUmbrella(), acWeatherData.isUmbrella());
	}
	
	@Test(expected =  HttpClientErrorException.class)
	public void getCurrentWeatherDataWithHttpError() {

		String location = "Berlin";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		System system = new System();
		system.setCountry("De");

		Main main = new Main();
		main.setPressure(12.0f);
		main.setTemp(12.0f);

		WeatherDto dto = new WeatherDto();
		dto.setMain("Cloud");

		List<WeatherDto> dtos = new ArrayList<>();
		dtos.add(dto);

		CurrentWeatherResponseDto currentWeatherResponseDto = new CurrentWeatherResponseDto();
		currentWeatherResponseDto.setMain(main);
		currentWeatherResponseDto.setSys(system);
		currentWeatherResponseDto.setWeather(dtos);
		currentWeatherResponseDto.setName(location);

		ResponseEntity<CurrentWeatherResponseDto> responseEntity = new ResponseEntity<CurrentWeatherResponseDto>(
				currentWeatherResponseDto, HttpStatus.OK);

		PowerMockito
				.when(restTemplate.getForEntity("http://http/owp/?q=Berlin&appid=123", CurrentWeatherResponseDto.class))
				.thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

	openWeatherMapProvider.getCurrentWeatherData(location);

	}

}
