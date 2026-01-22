package model;

/**
 * Weather contains: - name of the city, longitude, latitude
 */
public class Weather {
    private String iconCode;
    private String description;
    private String temperature;
    private String feelsLikeTemperature;
    private String windSpeed;
    private String cloudiness;
    private String humidity;
    private String pressure;

    public Weather(String iconCode, String description, String temperature, String feelsLikeTemperature, String windSpeed, String cloudiness, String humidity, String pressure) {
        this.iconCode = iconCode;
        this.description = description;
        this.temperature = temperature;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.windSpeed = windSpeed;
        this.cloudiness = cloudiness;
        this.humidity = humidity;
        this.pressure = pressure;
    }
}
