package krawczyk.grzegorz.model;

/**
 * Complete weather data for a location.to be displayed in current weather section.
 * It contains Location object, CurrentWeather object and WeatherForecast object.
 */
public class WeatherData {
    Location location;
    CurrentWeather currentWeather;
    WeatherForecast weatherForecast;

    /**
     * Constructor of the class WeatherData.
     * It initializes location object in the class. Rest of objects is not initialized - they are null.
     * It is used to initialize location and set if it is currentLocation or destination.
     * @param location (Location) - location for which weather will be checked.
     *                 You can pass new Location object with just isCurrentLocation argument.
     */
    public WeatherData(Location location) {
        this.location = location;
    }

    /**
     * Constructor of the class WeatherData.
     * @param location - object of class Location
     * @param currentWeather - object of class CurrentWeather
     * @param weatherForecast - object of class WeatherForecast
     */
    public WeatherData(Location location, CurrentWeather currentWeather, WeatherForecast weatherForecast) {
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
     * Returns Location object
     * @return
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns CurrentWeather object
     * @return
     */
    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    /**
     * Returns WeatherForecast object
     * @return
     */
    public WeatherForecast getWeatherForecast() {
        return weatherForecast;
    }
}
