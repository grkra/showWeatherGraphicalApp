package krawczyk.grzegorz.model;

import java.io.Serializable;

/**
 * Complete weather data for a location.to be displayed in current weather section.
 * It contains Location object, CurrentWeather object and WeatherForecast object.
 */
public class WeatherData implements Serializable {
    boolean isCurrentLocation;
    Location location;
    CurrentWeather currentWeather;
    WeatherForecast weatherForecast;

    /**
     * Constructor of the class WeatherData.
     * It initializes location object in the class. Rest of objects is not initialized - they are null.
     * It is used to initialize location.
     * @param location (Location) - location for which weather will be checked.
     *                 You can pass new Location object with no parameters..
     */
    public WeatherData(Location location) {
        this.location = location;
    }

    /**
     * Constructor of the class WeatherData.
     *
     * @param isCurrentLocation - if it is weather for current location or destination
     * @param location        - object of class Location
     * @param currentWeather  - object of class CurrentWeather
     * @param weatherForecast - object of class WeatherForecast
     */
    public WeatherData(boolean isCurrentLocation, Location location, CurrentWeather currentWeather, WeatherForecast weatherForecast) {
        this.isCurrentLocation = isCurrentLocation;
        this.location = location;
        this.currentWeather = currentWeather;
        this.weatherForecast = weatherForecast;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public void setWeatherForecast(WeatherForecast weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

    /**
     * Returns true if it is weather for current location, false if it is for destination
     * @return
     */
    public boolean getIsCurrentLocation() {
        return isCurrentLocation;
    }

    /**
     * Returns Location object
     *
     * @return
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns CurrentWeather object
     *
     * @return
     */
    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    /**
     * Returns WeatherForecast object
     *
     * @return
     */
    public WeatherForecast getWeatherForecast() {
        return weatherForecast;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "location=" + location +
                ", currentWeather=" + currentWeather +
                ", weatherForecast=" + weatherForecast +
                '}';
    }
}
