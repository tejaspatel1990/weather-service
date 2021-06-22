package com.weatherservice.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.test.util.ReflectionTestUtils;

import com.weatherservice.apicore.exception.ApplicationErrorException;
import com.weatherservice.apicore.exception.NoDataFoundException;
import com.weatherservice.model.WeatherData;
import com.weatherservice.model.WeatherHistory;
import com.weatherservice.repository.WeatherDataCustomRepository;
import com.weatherservice.repository.WeatherDataRepository;
import com.weatherservice.util.WeatherDataUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.weatherservice.*")
public class WeatherDataServiceImplTest {

	@Mock
	private WeatherDataManager weatherDataManager;

	@Mock
	private WeatherDataRepository weatherDataRepository;

	@Mock
	private WeatherDataCustomRepository weatherDataCustomRepository;

	@InjectMocks
	private WeatherDataServiceImpl weatherDataServiceImpl;

	@Before
	public void before() {
		ReflectionTestUtils.setField(weatherDataServiceImpl, "limit", 5);
	}

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

	@Test
	public void getWeatherHistorySuccessfully() throws Exception {
		String location = "Berlin";
		List<WeatherData> datas = new ArrayList<>();

		WeatherData weatherDataFilter = new WeatherData();
		weatherDataFilter.setCityName(location);
		// weatherDataFilter.setCountry("De");

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

		PowerMockito.mockStatic(WeatherDataUtils.class);
		PowerMockito.when(WeatherDataUtils.getWeatherDataFilterFromLocation(location)).thenReturn(weatherDataFilter);
		WeatherDataServiceImpl mock = PowerMockito.spy(weatherDataServiceImpl);
		PowerMockito.doReturn(datas).when(mock).getListOfWeatherData(weatherDataFilter, 5);
		PowerMockito.doReturn(history).when(mock, "computeAndBuildWeatherHistory", datas);
		WeatherHistory actualWeatherHistory = mock.getWeatherHistory(location, 5);

		assertNotNull(actualWeatherHistory);
		assertEquals(actualWeatherHistory.getAveragePressure(), history.getAveragePressure());
		assertEquals(actualWeatherHistory.getHistory().size(), 2);
	}

	@Test
	public void getWeatherHistoryWithNoLimit() throws Exception {
		String location = "Berlin";
		List<WeatherData> datas = new ArrayList<>();

		WeatherData weatherDataFilter = new WeatherData();
		weatherDataFilter.setCityName(location);
		// weatherDataFilter.setCountry("De");

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

		PowerMockito.mockStatic(WeatherDataUtils.class);
		PowerMockito.when(WeatherDataUtils.getWeatherDataFilterFromLocation(location)).thenReturn(weatherDataFilter);
		WeatherDataServiceImpl mock = PowerMockito.spy(weatherDataServiceImpl);
		PowerMockito.doReturn(datas).when(mock).getListOfWeatherData(weatherDataFilter, 5);
		PowerMockito.doReturn(history).when(mock, "computeAndBuildWeatherHistory", datas);
		WeatherHistory actualWeatherHistory = mock.getWeatherHistory(location, 0);

		assertNotNull(actualWeatherHistory);
		assertEquals(actualWeatherHistory.getAveragePressure(), history.getAveragePressure());
		assertEquals(actualWeatherHistory.getHistory().size(), 2);
	}

	@Test
	public void getWeatherHistoryWithLimitAsNull() throws Exception {
		String location = "Berlin";
		List<WeatherData> datas = new ArrayList<>();

		WeatherData weatherDataFilter = new WeatherData();
		weatherDataFilter.setCityName(location);
		// weatherDataFilter.setCountry("De");

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

		PowerMockito.mockStatic(WeatherDataUtils.class);
		PowerMockito.when(WeatherDataUtils.getWeatherDataFilterFromLocation(location)).thenReturn(weatherDataFilter);
		WeatherDataServiceImpl mock = PowerMockito.spy(weatherDataServiceImpl);
		PowerMockito.doReturn(datas).when(mock).getListOfWeatherData(weatherDataFilter, 5);
		PowerMockito.doReturn(history).when(mock, "computeAndBuildWeatherHistory", datas);
		WeatherHistory actualWeatherHistory = mock.getWeatherHistory(location, null);

		assertNotNull(actualWeatherHistory);
		assertEquals(actualWeatherHistory.getAveragePressure(), history.getAveragePressure());
		assertEquals(actualWeatherHistory.getHistory().size(), 2);
	}

	@Test
	public void getListOfWeatherDataSuccessfully() {
		String location = "Berlin";
		List<WeatherData> datas = new ArrayList<>();

		WeatherData weatherDataFilter = new WeatherData();
		weatherDataFilter.setCityName(location);
		// weatherDataFilter.setCountry("De");

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

		PowerMockito.when(weatherDataCustomRepository.getWeatherHistory(weatherDataFilter, 5)).thenReturn(datas);

		List<WeatherData> actualWeatherDatas = weatherDataServiceImpl.getListOfWeatherData(weatherDataFilter, 5);

		assertNotNull(actualWeatherDatas);
		assertEquals(2, actualWeatherDatas.size());
	}

	@Test(expected = NoDataFoundException.class)
	public void getListOfWeatherDataWithNoDataFoundExcetion() {
		String location = "Berlin";
		List<WeatherData> datas = new ArrayList<>();

		WeatherData weatherDataFilter = new WeatherData();
		weatherDataFilter.setCityName(location);
		// weatherDataFilter.setCountry("De");

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

		PowerMockito.when(weatherDataCustomRepository.getWeatherHistory(weatherDataFilter, 5))
				.thenThrow(new NoDataFoundException("xyz"));

		weatherDataServiceImpl.getListOfWeatherData(weatherDataFilter, 5);
	}
}
