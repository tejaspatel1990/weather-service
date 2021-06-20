package com.weatherservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weatherservice.model.CurrentWeatherData;

public interface WeatherDataRepository extends JpaRepository<CurrentWeatherData, Long> {
	

}
