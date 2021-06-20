package com.weatherservice.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.weatherservice.model.WeatherData;
import com.weatherservice.repository.WeatherDataCustomRepository;

@Repository
public class WeatherDataCutomRepositoryImpl implements WeatherDataCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<WeatherData> getWeatherHistory(WeatherData weatherData, int limit) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("Select c from WeatherData c where lower(c.cityName) = lower(:cityName)");

		if (weatherData.getCountry() != null && !weatherData.getCountry().isEmpty()) {
			sqlBuilder.append(" and lower(c.country) = lower(:country)");
		}

		sqlBuilder.append(" Order By c.createdAt desc");

		TypedQuery<WeatherData> query = entityManager.createQuery(sqlBuilder.toString(), WeatherData.class);

		query.setParameter("cityName", weatherData.getCityName());

		if (weatherData.getCountry() != null && !weatherData.getCountry().isEmpty()) {
			query.setParameter("country", weatherData.getCountry());
		}

		List<WeatherData> weatherDatas = query.setMaxResults(limit).getResultList();
		return weatherDatas;
	}

}
