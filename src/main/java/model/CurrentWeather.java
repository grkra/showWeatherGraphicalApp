package model;

/**
 * Weather data to be displayed in current weather section.
 * Contains: code to display icon, description, temperature, feels like temperature, wind speed, cloudiness, humidity, pressure.
 */
public class CurrentWeather {
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
}
