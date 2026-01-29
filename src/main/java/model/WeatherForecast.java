package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Weather data form a city to be displayed in weather forecast section.
 * Contains: city name and list of weather forecasts for each day.
 */
public class WeatherForecast {

    private String cityname;
    private List<SingleDayWeatherForecast> weatherForecastEntries;

    public WeatherForecast(String cityname) {
        this.cityname = cityname;
        this.weatherForecastEntries = new ArrayList<>();
    }

    public void addWeatherForecastEntry (SingleDayWeatherForecast singleDayWeatherForecast) {
        this.weatherForecastEntries.add(singleDayWeatherForecast);
    }

    public String getCityname() {
        return cityname;
    }

    public List<SingleDayWeatherForecast> getWeatherForecastEntries() {
        return weatherForecastEntries;
    }

    @Override
    public String toString() {
        return "WeatherForecast{" +
                "cityname='" + cityname + '\'' +
                ", weatherForecastEntries=" + weatherForecastEntries +
                '}';
    }
}


