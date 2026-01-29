package model;

import java.time.LocalDate;

/**
 * Simple weather data forecast for 1 day to be displayed in weather forecast section.
 * Contains: date, code to display icon, temperature.
 */
public class SingleDayWeatherForecast {
    private LocalDate localDate;
    private String iconCode;
    private String feelsLikeTemperature;

    public SingleDayWeatherForecast(LocalDate localDate, String iconCode, String feelsLikeTemperature) {
        this.localDate = localDate;
        this.iconCode = iconCode;
        this.feelsLikeTemperature = feelsLikeTemperature;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public String getIconCode() {
        return iconCode;
    }

    public String getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }

    @Override
    public String toString() {
        return "SingleDayWeatherForecast{" +
                "localDate=" + localDate +
                ", iconCode='" + iconCode + '\'' +
                ", temperature='" + feelsLikeTemperature + '\'' +
                '}';
    }
}
