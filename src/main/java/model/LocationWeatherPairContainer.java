package model;

/**
 * Container for a pair of objects: Location and Weather.
 * Used to return 2 objects from GetCurrentWeatherService.
 */
public class LocationWeatherPairContainer {
    private Location location;
    private Weather weather;

    public LocationWeatherPairContainer(Location location, Weather weather) {
        this.location = location;
        this.weather = weather;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
