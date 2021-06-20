package com.weatherservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weatherservice.model.WeatherData;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
	

}
