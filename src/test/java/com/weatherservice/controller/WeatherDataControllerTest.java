package com.weatherservice.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;

import com.weatherservice.apicore.config.ApiCoreGlobalExceptionHandler;
import com.weatherservice.apicore.exception.ApplicationErrorException;
import com.weatherservice.apicore.exception.NoDataFoundException;
import com.weatherservice.apicore.util.ApiCorei18nMessageUtil;
import com.weatherservice.model.WeatherData;
import com.weatherservice.model.WeatherHistory;
import com.weatherservice.service.WeatherDataService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherDataControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private ApiCorei18nMessageUtil apiCorei18nMessageUtil;

	@Mock
	private WeatherDataService weatherDataService;

	@InjectMocks
	private WeatherDataController controller;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new ApiCoreGlobalExceptionHandler().withMessageUtil(apiCorei18nMessageUtil))
				.build();
	}

	@Test
	public void getCurrentWeatherDataForCitySuccessfully() throws Exception {

		String location = "Berlin";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		PowerMockito.when(weatherDataService.getCurrentWeatherData(location)).thenReturn(weatherData);

		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("location", "Berlin");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/weather/current").params(requestParams);
		mockMvc.perform(requestBuilder).andExpect(status().isOk());

	}

	@Test
	public void getCurrentWeatherDataForCityAndCountrySuccessfully() throws Exception {

		String location1 = "Berlin,de";

		WeatherData we1atherData = new WeatherData();
		we1atherData.setCityName(location1);
		we1atherData.setCountry("De");
		we1atherData.setTemparature(13.0f);
		we1atherData.setPressure(12.0f);
		we1atherData.setUmbrella(false);

		PowerMockito.when(weatherDataService.getCurrentWeatherData(location1)).thenReturn(we1atherData);

		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("location", location1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/weather/current").params(requestParams);
		mockMvc.perform(requestBuilder).andExpect(status().isOk());

	}

	@Test
	public void getCurrentWeatherDataWithoutLocation() throws Exception {

		String location = "Berlin,de";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		PowerMockito.when(weatherDataService.getCurrentWeatherData(location)).thenReturn(weatherData);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/weather/current");
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());

	}

	@Test
	public void getCurrentWeatherDataWithNoDataFound() throws Exception {

		String location = "Berlin,de";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		PowerMockito.when(weatherDataService.getCurrentWeatherData(location))
				.thenThrow(new NoDataFoundException("xyz"));
		PowerMockito.when(apiCorei18nMessageUtil.getLocalizeMessage("xyz")).thenReturn(" Missing");

		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("location", location);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/weather/current").params(requestParams);
		mockMvc.perform(requestBuilder).andExpect(status().isNotFound());

	}

	@Test
	public void getCurrentWeatherDataWithIntervalServerErrorException() throws Exception {

		String location = "Berlin,de";

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		PowerMockito.when(weatherDataService.getCurrentWeatherData(location))
				.thenThrow(new ApplicationErrorException("xyz"));
		PowerMockito.when(apiCorei18nMessageUtil.getLocalizeMessage("xyz")).thenReturn(" Missing");

		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("location", location);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/weather/current").params(requestParams);
		mockMvc.perform(requestBuilder).andExpect(status().is5xxServerError());

	}

	@Test
	public void getWeatherHistorySuccessfully() throws Exception {
		String location = "Berlin";
		List<WeatherData> datas = new ArrayList<>();

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		datas.add(weatherData);

		WeatherHistory history = new WeatherHistory();
		history.setAveragePressure(12.0f);
		history.setAveragePressure(13.0f);
		history.setHistory(datas);

		PowerMockito.when(weatherDataService.getWeatherHistory(location, 5)).thenReturn(history);

		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("location", location);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/weather/history").params(requestParams);
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}
	
	@Test
	public void getWeatherHistoryWithCityAndLimit() throws Exception {
		String location = "Berlin";
		List<WeatherData> datas = new ArrayList<>();

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		datas.add(weatherData);

		WeatherHistory history = new WeatherHistory();
		history.setAveragePressure(12.0f);
		history.setAveragePressure(13.0f);
		history.setHistory(datas);

		PowerMockito.when(weatherDataService.getWeatherHistory(location, 5)).thenReturn(history);

		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("location", location);
		requestParams.add("limit", "5");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/weather/history").params(requestParams);
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}
	
	@Test
	public void getWeatherHistoryWithoutCity() throws Exception {
		String location = "Berlin";
		List<WeatherData> datas = new ArrayList<>();

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		datas.add(weatherData);

		WeatherHistory history = new WeatherHistory();
		history.setAveragePressure(12.0f);
		history.setAveragePressure(13.0f);
		history.setHistory(datas);

		PowerMockito.when(weatherDataService.getWeatherHistory(location, 5)).thenReturn(history);

		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("location", location);
		requestParams.add("limit", "5");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/weather/history");
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
	}
	
	@Test
	public void getWeatherHistoryWHereNoDataIsFound() throws Exception {
		String location = "Berlin";
		List<WeatherData> datas = new ArrayList<>();

		WeatherData weatherData = new WeatherData();
		weatherData.setCityName(location);
		weatherData.setCountry("De");
		weatherData.setTemparature(12.0f);
		weatherData.setPressure(12.0f);
		weatherData.setUmbrella(false);

		datas.add(weatherData);

		WeatherHistory history = new WeatherHistory();
		history.setAveragePressure(12.0f);
		history.setAveragePressure(13.0f);
		history.setHistory(datas);

		PowerMockito.when(weatherDataService.getWeatherHistory(location, 5)).thenThrow(new NoDataFoundException("xyz"));
		PowerMockito.when(apiCorei18nMessageUtil.getLocalizeMessage("xyz")).thenReturn(" Missing");

		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("location", location);
		requestParams.add("limit", "5");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/weather/history").params(requestParams);
		mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
	}

}
