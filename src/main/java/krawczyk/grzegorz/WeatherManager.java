package krawczyk.grzegorz;

import krawczyk.grzegorz.model.WeatherData;

public class WeatherManager {
    private WeatherData currentLocationWeather;
    private WeatherData destinationWeather;

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
}
