package com.weatherservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Data transfer object class for storing current weather information
 * 
 * @author tejas
 *
 */
@ApiModel(description = "Current weather data model")
public class CurrentWeatherData {

	@ApiModelProperty(name = "cityName", notes = "Name of the city", example = "London")
	private String cityName;

	@ApiModelProperty(name = "country", notes = "Country code", example = "GB")
	private String country;

	@ApiModelProperty(name = "temparature", notes = "Current Temperature", example = "12.10")
	private Float temparature;

	@ApiModelProperty(name = "pressure", notes = "Current Presuure", example = "45.10")
	private Float pressure;

	@ApiModelProperty(name = "umbrella", notes = "Depending of the whether condition this gives you true or false to carry umbrella ", example = "true")
	private boolean umbrella;

	public Float getTemparature() {
		return temparature;
	}

	public void setTemparature(Float temp) {
		this.temparature = temp;
	}

	public Float getPressure() {
		return pressure;
	}

	public void setPressure(Float pressure) {
		this.pressure = pressure;
	}

	public boolean isUmbrella() {
		return umbrella;
	}

	public void setUmbrella(boolean umbrella) {
		this.umbrella = umbrella;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
