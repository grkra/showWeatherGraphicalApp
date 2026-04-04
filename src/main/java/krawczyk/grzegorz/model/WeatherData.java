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
     * It initializes location object in the class and isCurrentLocation property.
     * Rest of objects is not initialized - they are null.
     * It is used to initialize:
     * <ol>
     *     <li>
     *         location property (it is necessary in getWeather button actions in case if there was no persisted file to load),
     *     </li>
     *     <li>
     *         isCurrentLocation (it is used to pass data to getWeather API in getWeather button actions).
     *     </li>
     * </ol>
     *
     * @param location (Location) - location for which weather will be checked.
     *                 You can pass new Location object with no parameters.
     * @param isCurrentLocation (boolean) - true for current location data, false for destination data/
     */
    public WeatherData(Location location, boolean isCurrentLocation) {
        this.location = location;
        this.isCurrentLocation = isCurrentLocation;
    }

    /**
     * Constructor of the class WeatherData.
     *
     * @param isCurrentLocation - if it is weather for current location or destination
     * @param location          - object of class Location
     * @param currentWeather    - object of class CurrentWeather
     * @param weatherForecast   - object of class WeatherForecast
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

    /**
     * Checks if it is weather for current location or destination.
     *
     * @return (boolean) true for current location, false for destination.
     */
    public boolean getIsCurrentLocation() {
        return isCurrentLocation;
    }

    /**
     * Returns Location object.
     *
     * @return (Location) location (city) for which this weather data was checked.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns CurrentWeather object.
     *
     * @return (CurrentData) weather data for current time.
     */
    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    /**
     * Returns WeatherForecast object.
     *
     * @return (WeatherForecast) weather forecast data for 5 days.
     */
    public WeatherForecast getWeatherForecast() {
        return weatherForecast;
    }
}
