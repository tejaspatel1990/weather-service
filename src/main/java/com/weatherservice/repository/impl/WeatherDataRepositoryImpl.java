package com.weatherservice.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.weatherservice.model.CurrentWeatherData;
import com.weatherservice.repository.WeatherDataRepository;

@Repository
public class WeatherDataRepositoryImpl implements WeatherDataRepository{

	@Override
	public List<CurrentWeatherData> findWeatherDataByCity(String location, int limit) {
		CurrentWeatherData cw1 = new CurrentWeatherData();
		cw1.setPressure(12.0f);
		cw1.setTemparature(10.0f);
		cw1.setUmbrella(true);
		
		CurrentWeatherData cw2 = new CurrentWeatherData();
		cw2.setPressure(13.0f);
		cw2.setTemparature(11.0f);
		cw2.setUmbrella(true);
		
		CurrentWeatherData cw3 = new CurrentWeatherData();
		cw3.setPressure(13.0f);
		cw3.setTemparature(13.0f);
		cw3.setUmbrella(true);
		
		CurrentWeatherData cw4 = new CurrentWeatherData();
		cw4.setPressure(14.0f);
		cw4.setTemparature(14.0f);
		cw4.setUmbrella(true);
		
		CurrentWeatherData cw5 = new CurrentWeatherData();
		cw5.setPressure(15.0f);
		cw5.setTemparature(150f);
		cw5.setUmbrella(true);
		
		List<CurrentWeatherData> list = new ArrayList<CurrentWeatherData>();
		list.add(cw1);
		list.add(cw2);
		list.add(cw3);
		list.add(cw4);
		list.add(cw5);
		
		return list;
	}

}
