package com.weatherservice.provider.openweathermap.util;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class OpenWeatherMapUtils {

	private OpenWeatherMapUtils() {

	}

	/**
	 * Check whtehr to take umbrella or not
	 * 
	 * @param currentWeather
	 * @param umbrellaCondition
	 * @return
	 */
	public static boolean takeUmbrella(String currentWeather, String umbrellaCondition) {
		Set<String> umbrellaConditionList = Stream.of(umbrellaCondition.split(",")).collect(Collectors.toSet());

		return umbrellaConditionList.contains(currentWeather);
	}
}
