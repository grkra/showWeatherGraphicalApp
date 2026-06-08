package krawczyk.grzegorz.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Weather data to be displayed in current weather section.
 * Contains: code to display icon, description, temperature, feels like temperature, wind speed, cloudiness, humidity, pressure.
 */
public class CurrentWeather implements Serializable {
    private String iconCode;
    private String description;
    private String temperature;
    private String feelsLikeTemperature;
    private String windSpeed;
    private String cloudiness;
    private String humidity;
    private String pressure;

    public CurrentWeather(String iconCode, String description, String temperature, String feelsLikeTemperature, String windSpeed, String cloudiness, String humidity, String pressure) {
        this.iconCode = iconCode;
        this.description = description;
        this.temperature = temperature;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.windSpeed = windSpeed;
        this.cloudiness = cloudiness;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public String getIconCode() {
        return iconCode;
    }

    public String getDescription() {
        return description;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getCloudiness() {
        return cloudiness;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CurrentWeather that = (CurrentWeather) o;
        return Objects.equals(iconCode, that.iconCode) && Objects.equals(description, that.description) && Objects.equals(temperature, that.temperature) && Objects.equals(feelsLikeTemperature, that.feelsLikeTemperature) && Objects.equals(windSpeed, that.windSpeed) && Objects.equals(cloudiness, that.cloudiness) && Objects.equals(humidity, that.humidity) && Objects.equals(pressure, that.pressure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iconCode, description, temperature, feelsLikeTemperature, windSpeed, cloudiness, humidity, pressure);
    }
}
