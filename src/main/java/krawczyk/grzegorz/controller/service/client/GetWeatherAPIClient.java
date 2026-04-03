package krawczyk.grzegorz.controller.service.client;

import krawczyk.grzegorz.model.Location;
import krawczyk.grzegorz.model.WeatherData;

import java.io.IOException;

/**
 * Interface used as abstraction layer between GetWeatherService and concrete GetWeatherAPIClient.
 * All GetWeatherAPIClients should implement this interface.
 * <p>
 * API must provide weather forecast based on name of a city or geographical coordinates (longitude and latitude).
 * API must be able to send back:
 * - current weather (code to display icon presenting weather, weather description, temperature in Celsius, feels like temperature in Celsius,
 * wind speed in m/s, cloudiness in %, humidity in %, air pressure in hPa)
 * - weather forecast for 5 days (code to display icon presenting weather, feels like temperature in Celsius).
 * <p>
 * Additionally, API can send back location (city name, latitude, longitude). If API doesn't send location, method should provide them other way.
 */
public interface GetWeatherAPIClient {

    /**
     * Method is used to send request to API providing weather forecast.
     * API must provide weather forecast based on name of a city or geographical coordinates (longitude and latitude).
     * API must be able to send back:
     * - current weather (code to display icon presenting weather, weather description, temperature in Celsius, feels like temperature in Celsius,
     * wind speed in m/s, cloudiness in %, humidity in %, air pressure in hPa)
     * - weather forecast for 5 days (code to display icon presenting weather, feels like temperature in Celsius).
     * <p>
     * Additionally, API can send back location (city name, latitude, longitude). If API doesn't send location, method should provide them other way.
     *
     * @return new WeatherData object containing CurrentWeather, WeatherForecast, Location.
     * @throws IOException
     */
    WeatherData getWeather(Location location, boolean isCurrentLocation) throws IOException;
}
