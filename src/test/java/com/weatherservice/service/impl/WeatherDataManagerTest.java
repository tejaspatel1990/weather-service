package com.weatherservice.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.weatherservice.apicore.exception.ApplicationErrorException;
import com.weatherservice.apicore.exception.NoDataFoundException;
import com.weatherservice.exception.CircuitBreakerFallBackException;
import com.weatherservice.model.WeatherData;
import com.weatherservice.provider.internal.impl.InternalWeatherProvider;
import com.weatherservice.provider.openweathermap.impl.OpenWeatherMapProvider;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.weatherservice.*")
public class WeatherDataManagerTest {

	@Mock
	private OpenWeatherMapProvider openWeatherMapProvider;

	@Mock
	private InternalWeatherProvider internalWeatherProvider;

	@InjectMocks
	private WeatherDataManager weatherDataManager;

	@Test
	public void getCurrentWeatherDataSuccessfully() {
		String location = "Berlin";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		PowerMockito.when(openWeatherMapProvider.getCurrentWeatherData(location)).thenReturn(weatherData);

		WeatherData actualWeatherData = weatherDataManager.getCurrentWeatherData(location);
		assertNotNull(actualWeatherData);
		assertEquals(weatherData.getCityName(), actualWeatherData.getCityName());
		assertEquals(weatherData.getPressure(), actualWeatherData.getPressure());

	}

	@Test(expected = NoDataFoundException.class)
	public void getCurrentWeatherDataWith404() {
		String location = "Berlin";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		PowerMockito.when(openWeatherMapProvider.getCurrentWeatherData(location))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

		weatherDataManager.getCurrentWeatherData(location);

	}

	@Test(expected = ApplicationErrorException.class)
	public void getCurrentWeatherDataWith401() {
		String location = "Berlin";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		PowerMockito.when(openWeatherMapProvider.getCurrentWeatherData(location))
				.thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

		weatherDataManager.getCurrentWeatherData(location);

	}

	@Test(expected = ApplicationErrorException.class)
	public void getCurrentWeatherDataWith429() {
		String location = "Berlin";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		PowerMockito.when(openWeatherMapProvider.getCurrentWeatherData(location))
				.thenThrow(new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS));

		weatherDataManager.getCurrentWeatherData(location);

	}

	@Test
	public void getCurrentWeatherDataWithCircuitBreakerException() {
		String location = "Berlin";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);

		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		PowerMockito.when(internalWeatherProvider.getCurrentWeatherData(location)).thenReturn(weatherData);
		
		PowerMockito.when(openWeatherMapProvider.getCurrentWeatherData(location))
				.thenThrow(new CircuitBreakerFallBackException("xyz",null));

		WeatherData actualWeatherData = weatherDataManager.getCurrentWeatherData(location);
		assertNotNull(actualWeatherData);
		assertEquals(weatherData.getCityName(), actualWeatherData.getCityName());
		assertEquals(weatherData.getPressure(), actualWeatherData.getPressure());

	}
}
