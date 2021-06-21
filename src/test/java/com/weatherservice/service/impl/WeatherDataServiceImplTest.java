package com.weatherservice.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.weatherservice.apicore.exception.ApplicationErrorException;
import com.weatherservice.apicore.exception.NoDataFoundException;
import com.weatherservice.model.WeatherData;
import com.weatherservice.model.WeatherHistory;
import com.weatherservice.repository.WeatherDataRepository;

@RunWith(PowerMockRunner.class)
@PrepareForTest(WeatherDataServiceImpl.class)
public class WeatherDataServiceImplTest {

	@Mock
	private WeatherDataManager weatherDataManager;

	@Mock
	private WeatherDataRepository weatherDataRepository;

	@InjectMocks
	private WeatherDataServiceImpl weatherDataServiceImpl;

	@Test
	public void getCurrentWeatherDataSuccessfully() throws Exception {

		String location = "Berlin";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		PowerMockito.when(weatherDataManager.getCurrentWeatherData(location)).thenReturn(weatherData);

		WeatherDataServiceImpl mock = PowerMockito.spy(weatherDataServiceImpl);

		PowerMockito.doNothing().when(mock, "saveWeatherData", ArgumentMatchers.any(WeatherData.class));

		WeatherData actualWeatherData = weatherDataServiceImpl.getCurrentWeatherData(location);

		assertNotNull(actualWeatherData);
		assertEquals(weatherData.getCityName(), actualWeatherData.getCityName());
		assertEquals(weatherData.getCountry(), actualWeatherData.getCountry());
		assertEquals(weatherData.getPressure(), actualWeatherData.getPressure());
		assertEquals(weatherData.getTemparature(), actualWeatherData.getTemparature());
		assertEquals(weatherData.isUmbrella(), actualWeatherData.isUmbrella());
	}

	@Test(expected = NoDataFoundException.class)
	public void getCurrentWeatherDataWithNoDataFound() throws Exception {

		String location = "Berlin";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		PowerMockito.when(weatherDataManager.getCurrentWeatherData(location)).thenThrow(NoDataFoundException.class);

		weatherDataServiceImpl.getCurrentWeatherData(location);

	}

	@Test(expected = ApplicationErrorException.class)
	public void getCurrentWeatherDataWithApplicationError() throws Exception {

		String location = "Berlin";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		PowerMockito.when(weatherDataManager.getCurrentWeatherData(location))
				.thenThrow(ApplicationErrorException.class);

		weatherDataServiceImpl.getCurrentWeatherData(location);

	}

	@Test
	public void computeAndBuildWeatherHistorySuccessfully() throws Exception {

		String location = "Berlin";
		List<WeatherData> datas = new ArrayList<>();

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		WeatherData weatherData2 = new WeatherData();
		weatherData2.setCityName(location);
		weatherData2.setCountry("De");
		weatherData2.setTemparature(12.0f);
		weatherData2.setPressure(12.0f);
		weatherData2.setUmbrella(false);

		datas.add(weatherData);
		datas.add(weatherData2);

		WeatherHistory history = new WeatherHistory();
		history.setAveragePressure(12.0f);
		history.setAveragePressure(12.0f);
		history.setHistory(datas);

		WeatherHistory actualHistory = Whitebox.invokeMethod(weatherDataServiceImpl, "computeAndBuildWeatherHistory",
				datas);

		assertEquals(2, actualHistory.getHistory().size());
		assertEquals(new Float(12.0f), actualHistory.getAveragePressure());
	}

}
