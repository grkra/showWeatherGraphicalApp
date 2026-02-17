package model;

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

    public Location getLocation() {
        return location;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public WeatherForecast getWeatherForecast() {
        return weatherForecast;
    }
}
