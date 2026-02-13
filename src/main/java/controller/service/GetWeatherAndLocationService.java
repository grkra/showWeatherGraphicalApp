package controller.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.CurrentWeather;
import model.Location;
import model.LocationWeatherPairContainer;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Controller responsible for getting current weather for the passed location.
 * It sends request to OpenWeatherApi to get current weather.
 * In addition to this it gets name and geographic coordinates (longitude and latitude) from OpenWeatherApi
 */
public class GetWeatherAndLocationService extends Service<LocationWeatherPairContainer> {

    /**
     * Name of a city for which weather is being checked.
     */
    private Location location;

    /**
     * Connection object used to send HTTP request and get responses from API.
     */
    private HttpClient httpClient;

    /**
     * Constructor of the GetSurrentWeather service.
     * It initializes http client object for the requests to API.
     */
    public GetWeatherAndLocationService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    @Override
    protected Task<LocationWeatherPairContainer> createTask() {
        return new Task<LocationWeatherPairContainer>() {
            @Override
            protected LocationWeatherPairContainer call() throws Exception {

                HttpRequest httpRequest;
                if (location.getLatitude().isBlank() || location.getLongitude().isBlank()) {
                    httpRequest = HttpRequest.newBuilder()
                            .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?q=" + location.getName() + "&appid=6699194108befabbceba16db27cb548c&units=metric"))
                            .timeout(Duration.ofSeconds(5))
                            .GET()
                            .build();
                } else {
                    httpRequest = HttpRequest.newBuilder()
                            .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=6699194108befabbceba16db27cb548c&units=metric"))
                            .timeout(Duration.ofSeconds(5))
                            .GET()
                            .build();
                }

                try {
                    HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                    int httpResponseStatusCode = httpResponse.statusCode();

                    if (httpResponseStatusCode >= 200 && httpResponseStatusCode < 300) {

                        ObjectMapper jsonMapper = new ObjectMapper();
                        JsonNode jsonRoot = jsonMapper.readTree(httpResponse.body());

                        // Creating new Location object with longitude and latitude from OpeneWeatherAPI response if they were empty in original one:
                        if (location.getLongitude().isBlank() || location.getLatitude().isBlank()) {
                            location = new Location(location.getName(), jsonRoot.get("coord").get("lon").asString(), jsonRoot.get("coord").get("lat").asString(), location.getIsCurrentLocation());
                        }

                        String iconCode = jsonRoot.get("weather").get(0).get("icon").asString();
                        String description = jsonRoot.get("weather").get(0).get("description").asString();
                        description = description.substring(0,1).toUpperCase() + description.substring(1);
                        String temperature = jsonRoot.get("main").get("temp").asString() + " °C";
                        String feelsLikeTemperature = jsonRoot.get("main").get("feels_like").asString() + " °C";
                        String windSpeed = jsonRoot.get("wind").get("speed").asString() + " m/s";
                        String cloudiness = jsonRoot.get("clouds").get("all").asString() + " %";
                        String humidity = jsonRoot.get("main").get("humidity").asString() + " %";
                        String pressure = jsonRoot.get("main").get("pressure").asString() + " hPa";

                        CurrentWeather currentWeather = new CurrentWeather(iconCode, description, temperature, feelsLikeTemperature, windSpeed, cloudiness, humidity, pressure);

                        return new LocationWeatherPairContainer(location, currentWeather);
                    } else {
                        throw new IOException("HTTP Error: " + httpResponseStatusCode);
                    }
                } catch (Exception e) {
                    throw new IOException("Connection error", e);
                }
            }
        };
    }

    /**
     * Method sets Location object. Location is necessary to send API request.
     * First set location, then start service.
     * @param location - (String) name of a city to check weather.
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}
