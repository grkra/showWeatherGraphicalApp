package model;

import java.time.LocalDate;

/**
 * Simple weather data forecast for 1 day to be displayed in weather forecast section.
 * Contains: date, code to display icon, temperature.
 */
public class SingleDayWeatherForecast {
    private LocalDate localDate;
    private String iconCode;
    private String temperature;

    public SingleDayWeatherForecast(LocalDate localDate, String iconCode, String temperature) {
        this.localDate = localDate;
        this.iconCode = iconCode;
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "SingleDayWeatherForecast{" +
                "localDate=" + localDate +
                ", iconCode='" + iconCode + '\'' +
                ", temperature='" + temperature + '\'' +
                '}';
    }
}
