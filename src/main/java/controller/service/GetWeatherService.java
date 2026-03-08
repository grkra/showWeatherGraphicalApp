package controller.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.*;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for getting weather data for the passed location.
 * It sends request to OpenWeatherApi to get location, current weather, weather forecast.
 * It gets location form API to update longitude and latitude of a city based on city name
 * (if they weren't fetched from geolocation service yet).
 */
public class GetWeatherService extends Service<WeatherData> {

    /**
     * Location for which weather is being checked.
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
    public GetWeatherService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    @Override
    protected Task<WeatherData> createTask() {
        return new Task<WeatherData>() {
            @Override
            protected WeatherData call() throws Exception {

                HttpRequest httpRequestCurrentWether;
                HttpRequest httpRequestWeatherForecast;
                if (location.getLatitude().isBlank() || location.getLongitude().isBlank()) {
                    String locationNameNoSpaces = location.getName().replace(" ", "%20");
                    httpRequestCurrentWether = HttpRequest.newBuilder()
                            .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?q=" + locationNameNoSpaces + "&appid=6699194108befabbceba16db27cb548c&units=metric"))
                            .timeout(Duration.ofSeconds(5))
                            .GET()
                            .build();
                    httpRequestWeatherForecast = HttpRequest.newBuilder()
                            .uri(URI.create("https://api.openweathermap.org/data/2.5/forecast?q=" + locationNameNoSpaces + "&appid=6699194108befabbceba16db27cb548c&units=metric"))
                            .timeout(Duration.ofSeconds(5))
                            .GET()
                            .build();
                } else {
                    httpRequestCurrentWether = HttpRequest.newBuilder()
                            .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=6699194108befabbceba16db27cb548c&units=metric"))
                            .timeout(Duration.ofSeconds(5))
                            .GET()
                            .build();
                    httpRequestWeatherForecast = HttpRequest.newBuilder()
                            .uri(URI.create("https://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=6699194108befabbceba16db27cb548c&units=metric"))
                            .timeout(Duration.ofSeconds(5))
                            .GET()
                            .build();
                }

                try {
                    HttpResponse<String> httpResponseCurrentWeather = httpClient.send(httpRequestCurrentWether, HttpResponse.BodyHandlers.ofString());
                    HttpResponse<String> httpResponseWeatherForecast = httpClient.send(httpRequestWeatherForecast, HttpResponse.BodyHandlers.ofString());

                    CurrentWeather currentWeather = getCurrentWeatherFromResponse(httpResponseCurrentWeather);
                    WeatherForecast weatherForecast = getWeatherForecastFromResponse(httpResponseWeatherForecast);

                    if (location.getLongitude().isBlank() || location.getLatitude().isBlank()) {
                        location = getLocationFromResponse(httpResponseCurrentWeather);
                    }

                    return new WeatherData(location, currentWeather, weatherForecast);
                } catch (Exception e) {
                    throw new IOException("Connection error", e);
                }
            }
        };
    }

    /**
     * Method parses http response with current weather from openWeatherApi to CurrentWeather object.
     *
     * @param httpResponseCurrentWeather (HttpResponse<String>) - http response with current weather from openWeatherApi
     * @return new CurrentWeather object
     * @throws IOException
     */
    private CurrentWeather getCurrentWeatherFromResponse(HttpResponse<String> httpResponseCurrentWeather) throws IOException {
        int httpResponseStatusCode = httpResponseCurrentWeather.statusCode();

        if (httpResponseStatusCode >= 200 && httpResponseStatusCode < 300) {

            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode jsonRoot = jsonMapper.readTree(httpResponseCurrentWeather.body());

            String iconCode = jsonRoot.get("weather").get(0).get("icon").asString();
            String description = jsonRoot.get("weather").get(0).get("description").asString();
            description = description.substring(0, 1).toUpperCase() + description.substring(1);
            String temperature = jsonRoot.get("main").get("temp").asString();
            String feelsLikeTemperature = jsonRoot.get("main").get("feels_like").asString();
            String windSpeed = jsonRoot.get("wind").get("speed").asString();
            String cloudiness = jsonRoot.get("clouds").get("all").asString();
            String humidity = jsonRoot.get("main").get("humidity").asString();
            String pressure = jsonRoot.get("main").get("pressure").asString();

            return new CurrentWeather(iconCode, description, temperature, feelsLikeTemperature, windSpeed, cloudiness, humidity, pressure);
        } else {
            throw new IOException("HTTP Error: " + httpResponseStatusCode);
        }
    }

    /**
     * Method parses http response with weather forecast from openWeatherApi to WeatherForecast object.
     *
     * @param httpResponseWeatherForecast (HttpResponse<String>) - http response with weather forecast from openWeatherApi
     * @return new WeatherForecast object
     * @throws IOException
     */
    private WeatherForecast getWeatherForecastFromResponse(HttpResponse<String> httpResponseWeatherForecast) throws IOException {
        int httpResponseStatusCode = httpResponseWeatherForecast.statusCode();

        if (httpResponseStatusCode >= 200 && httpResponseStatusCode < 300) {
            List<WeatherForecastSingleDayEntry> weatherEntriesForEachDay = new ArrayList<>();

            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode jsonRoot = jsonMapper.readTree(httpResponseWeatherForecast.body());
            JsonNode jsonNodeWeatherForecastList = jsonRoot.get("list");

            for (JsonNode jsonNodeWeatherForecastSingleDay : jsonNodeWeatherForecastList) {
                Instant instantDateTime = Instant.ofEpochSecond(jsonNodeWeatherForecastSingleDay.get("dt").asLong());
                ZonedDateTime zonedDateTime = instantDateTime.atZone(ZoneId.systemDefault());
                LocalDate localDate = zonedDateTime.toLocalDate();

                if (localDate.isAfter(LocalDate.now())
                        &&
                        zonedDateTime.getHour() >= 11 && zonedDateTime.getHour() <= 13) {

                    String iconCode = jsonNodeWeatherForecastSingleDay.get("weather").get(0).get("icon").asString();
                    String temperature = jsonNodeWeatherForecastSingleDay.get("main").get("temp").asString();

                    weatherEntriesForEachDay.add(new WeatherForecastSingleDayEntry(localDate, iconCode, temperature));
                }
            }

            return new WeatherForecast(weatherEntriesForEachDay);
        } else {
            throw new IOException("HTTP Error: " + httpResponseStatusCode);
        }
    }

    /**
     * Method parses http response with current weather from openWeatherApi to Location object.
     *
     * @param httpResponseCurrentWeather (HttpResponse<String>) - http response with current weather from openWeatherApi
     * @return new Location object
     * @throws IOException
     */
    private Location getLocationFromResponse(HttpResponse<String> httpResponseCurrentWeather) throws IOException {
        int httpResponseStatusCode = httpResponseCurrentWeather.statusCode();

        if (httpResponseStatusCode >= 200 && httpResponseStatusCode < 300) {

            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode jsonRoot = jsonMapper.readTree(httpResponseCurrentWeather.body());
            String longitude = jsonRoot.get("coord").get("lon").asString();
            String latitude = jsonRoot.get("coord").get("lat").asString();
            return new Location(location.getName(), longitude, latitude, location.getIsCurrentLocation());
        } else {
            throw new IOException("HTTP Error: " + httpResponseStatusCode);
        }
    }

    /**
     * Method sets Location object. Location is necessary to send API request.
     * First set location, then start service.
     *
     * @param location - (String) name of a city to check weather.
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}