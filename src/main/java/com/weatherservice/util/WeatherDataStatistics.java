package com.weatherservice.util;

import java.util.function.Consumer;

import com.weatherservice.model.CurrentWeatherData;

public class WeatherDataStatistics implements Consumer<CurrentWeatherData> {

	private float temparature = 0.0f;
	private float presuure = 0.0f;
	private int count = 0;

	@Override
	public void accept(CurrentWeatherData weatherData) {
		this.presuure += weatherData.getPressure();
		this.temparature += weatherData.getTemparature();
		count++;

	}

	public void combine(WeatherDataStatistics stats) {
		this.presuure += stats.presuure;
		this.temparature += stats.temparature;
		count += stats.count;
	}

	public final float getAveragePressure() {
		return getCount() > 0 ? (float) getPresuure() / getCount() : 0.0f;
	}

	public final float getAverageTemparature() {
		return getCount() > 0 ? (float) getTemparature() / getCount() : 0.0f;
	}

	public Float getTemparature() {
		return temparature;
	}

	public Float getPresuure() {
		return presuure;
	}

	public int getCount() {
		return count;
	}

}
