package krawczyk.grzegorz.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Simple weather data forecast for 1 day to be displayed in weather forecast section.
 * Contains: date, code to display icon, temperature.
 */
public class WeatherForecastSingleDayEntry implements Serializable {
    private LocalDate localDate;
    private String iconCode;
    private String feelsLikeTemperature;

    /**
     * Constructor of the class WeatherForecastSingleDayEntry
     * @param localDate (LocalDate) - date of this forecast
     * @param iconCode (String) - code to display icon
     * @param feelsLikeTemperature (String) - feels like temperature is Celsius
     */
    public WeatherForecastSingleDayEntry(LocalDate localDate, String iconCode, String feelsLikeTemperature) {
        this.localDate = localDate;
        this.iconCode = iconCode;
        this.feelsLikeTemperature = feelsLikeTemperature;
    }

    /**
     * Returns date of the forecast (Local date without timezone)
     * @return
     */
    public LocalDate getLocalDate() {
        return localDate;
    }

    /**
     * Retuns icon code (String) which is used to display icon
     * @return
     */
    public String getIconCode() {
        return iconCode;
    }

    /**
     * Returns feels like temperature in Celsius (String)
     * @return
     */
    public String getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }
}
