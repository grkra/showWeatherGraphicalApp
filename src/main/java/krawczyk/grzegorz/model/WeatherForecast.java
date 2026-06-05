package krawczyk.grzegorz.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Weather data form a city to be displayed in weather forecast section.
 * Contains: city name and list of weather forecasts for each day.
 */
public class WeatherForecast implements Serializable {

    private List<WeatherForecastSingleDayEntry> weatherForecastEntries;

    /**
     * Constructor of the class WeatherForecast.
     * @param weatherForecastEntries (List<WeatherForecastSingleDayEntry>) list of weather forecasts for each day.
     *                               Each day is separate element of the list.
     */
    public WeatherForecast(List<WeatherForecastSingleDayEntry> weatherForecastEntries) {
        this.weatherForecastEntries = weatherForecastEntries;
    }

    /**
     * Returns list of weather forecast (for each day)
     * @return
     */
    public List<WeatherForecastSingleDayEntry> getWeatherForecastEntries() {
        return weatherForecastEntries;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WeatherForecast that = (WeatherForecast) o;
        return Objects.equals(weatherForecastEntries, that.weatherForecastEntries);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(weatherForecastEntries);
    }
}


