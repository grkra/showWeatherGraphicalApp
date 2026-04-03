package krawczyk.grzegorz;

import krawczyk.grzegorz.model.WeatherData;

import java.io.Serializable;

public class WeatherManager implements Serializable {
    private WeatherData currentLocationWeather;
    private WeatherData destinationWeather;

    public WeatherManager(WeatherData currentLocationWeather, WeatherData destinationWeather) {
        this.currentLocationWeather = currentLocationWeather;
        this.destinationWeather = destinationWeather;
    }

    public WeatherData getCurrentLocationWeather() {
        return currentLocationWeather;
    }

    public void setCurrentLocationWeather(WeatherData currentLocationWeather) {
        this.currentLocationWeather = currentLocationWeather;
    }

    public WeatherData getDestinationWeather() {
        return destinationWeather;
    }

    public void setDestinationWeather(WeatherData destinationWeather) {
        this.destinationWeather = destinationWeather;
    }

    @Override
    public String toString() {
        return ("Current location: " + this.currentLocationWeather + ", destionation: " + this.destinationWeather);
    }
}
