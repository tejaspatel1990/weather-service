package com.weatherservice.model;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * DTO class for Weather history
 * 
 * @author tejas
 *
 */
@ApiModel(description = "Current weather data  history model")
public class WeatherHistory {

	@ApiModelProperty(name = "averageTemparature", notes = "Average Temperature", example = "12.10")
	Float averageTemparature;

	@ApiModelProperty(name = "averagePressure", notes = "Average Pressure", example = "15.10")
	Float averagePressure;

	@ApiModelProperty(name = "history", notes = "Weather history")
	List<WeatherData> history = new ArrayList<>();

	public Float getAverageTemparature() {
		return averageTemparature;
	}

	public void setAverageTemparature(Float averageTemparature) {
		this.averageTemparature = averageTemparature;
	}

	public Float getAveragePressure() {
		return averagePressure;
	}

	public void setAveragePressure(Float averagePresuure) {
		this.averagePressure = averagePresuure;
	}

	public List<WeatherData> getHistory() {
		return history;
	}

	public void setHistory(List<WeatherData> history) {
		this.history = history;
	}

}
