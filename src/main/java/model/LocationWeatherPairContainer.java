package model;

/**
 * Container for a pair of objects: Location and Weather.
 * Used to return 2 objects from GetCurrentWeatherService.
 */
public class LocationWeatherPairContainer {
    private Location location;
    private CurrentWeather currentWeather;

    public LocationWeatherPairContainer(Location location, CurrentWeather currentWeather) {
        this.location = location;
        this.currentWeather = currentWeather;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CurrentWeather getWeather() {
        return currentWeather;
    }

    public void setWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }
}
