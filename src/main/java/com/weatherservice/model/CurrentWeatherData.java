package com.weatherservice.model;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Data transfer object class for storing current weather information
 * 
 * @author tejas
 *
 */
@ApiModel(description = "Current weather data model")
@Entity
@Table(name = "weather_data")
public class CurrentWeatherData {

	@ApiModelProperty(name = "id", notes = "Internal Id", example = "12")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@ApiModelProperty(name = "cityName", notes = "Name of the city", example = "London")
	@Column(name = "city_name")
	private String cityName;

	@ApiModelProperty(name = "country", notes = "Country code", example = "GB")
	@Column(name = "country")
	private String country;

	@ApiModelProperty(name = "temparature", notes = "Current Temperature", example = "12.10")
	private Float temparature;

	@ApiModelProperty(name = "pressure", notes = "Current Presuure", example = "45.10")
	private Float pressure;

	@ApiModelProperty(name = "umbrella", notes = "Depending of the whether condition this gives you true or false to carry umbrella ", example = "true")
	private boolean umbrella;

	@CreationTimestamp
	private OffsetDateTime createdAt;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
