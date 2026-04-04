package krawczyk.grzegorz;

import krawczyk.grzegorz.model.WeatherData;

import java.io.Serializable;

/**
 * Class contains weather data for both: current location and destination.
 */
public class WeatherManager implements Serializable {
    private WeatherData currentLocationWeather;
    private WeatherData destinationWeather;

    /**
     * Constructor of class WeatherManager.
     * Constructor is used to initialize Location properties of both: currentLocationWeather and destinationWeather.
     * You can pass new WeatherData objects with new empty Location() objects.
     * @param currentLocationWeather (WeatherData) - weather data for current location.
     * @param destinationWeather (WeatherData) - weather data for destionation.
     */
    public WeatherManager(WeatherData currentLocationWeather, WeatherData destinationWeather) {
        this.currentLocationWeather = currentLocationWeather;
        this.destinationWeather = destinationWeather;
    }

    /**
     * Returns weather data for current location.
     * @return (WeatherData) - data for current location.
     */
    public WeatherData getCurrentLocationWeather() {
        return currentLocationWeather;
    }

    /**
     * Sets weather data for current location.
     * @param currentLocationWeather (WeatherData) - data for current location.
     */
    public void setCurrentLocationWeather(WeatherData currentLocationWeather) {
        this.currentLocationWeather = currentLocationWeather;
    }

    /**
     * Returns weather data for destination.
     * @return (WeatherData) - data for destination.
     */
    public WeatherData getDestinationWeather() {
        return destinationWeather;
    }

    /**
     * Sets weather data for destination.
     * @param destinationWeather (WeatherData) - data for destination.
     */
    public void setDestinationWeather(WeatherData destinationWeather) {
        this.destinationWeather = destinationWeather;
    }

    @Override
    public String toString() {
        return ("Current location: " + this.currentLocationWeather + ", destionation: " + this.destinationWeather);
    }
}
