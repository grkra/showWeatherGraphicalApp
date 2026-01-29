package controller.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.Location;
import model.WeatherForecast;
import model.SingleDayWeatherForecast;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.*;

public class GetWeatherForecastService extends Service<WeatherForecast> {

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
    public GetWeatherForecastService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    @Override
    protected Task<WeatherForecast> createTask() {
        return new Task<WeatherForecast>() {
            @Override
            protected WeatherForecast call() throws Exception {

                HttpRequest httpRequest;
                if (location.getLatitude().isBlank() || location.getLongitude().isBlank()) {
                    httpRequest = HttpRequest.newBuilder()
                            .uri(URI.create("https://api.openweathermap.org/data/2.5/forecast?q=" + location.getName() + "&appid=6699194108befabbceba16db27cb548c&units=metric"))
                            .timeout(Duration.ofSeconds(5))
                            .GET()
                            .build();
                } else {
                    httpRequest = HttpRequest.newBuilder()
                            .uri(URI.create("https://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=6699194108befabbceba16db27cb548c&units=metric"))
                            .timeout(Duration.ofSeconds(5))
                            .GET()
                            .build();
                }

                try {
                    HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                    int httpResponseStatusCode = httpResponse.statusCode();

                    if (httpResponseStatusCode >= 200 && httpResponseStatusCode < 300) {
                        WeatherForecast weatherForecast = new WeatherForecast(location.getName());

                        ObjectMapper jsonMapper = new ObjectMapper();
                        JsonNode jsonRoot = jsonMapper.readTree(httpResponse.body());
                        JsonNode list = jsonRoot.get("list");

                        for (JsonNode oneDayOnList : list) {
                            Instant instantDateTime = Instant.ofEpochSecond(oneDayOnList.get("dt").asLong());
                            ZonedDateTime zonedDateTime = instantDateTime.atZone(ZoneId.systemDefault());
                            LocalDate localDate = zonedDateTime.toLocalDate();
                            if (localDate.isAfter(LocalDate.now())
                                    &&
                                    zonedDateTime.getHour() >= 11 && zonedDateTime.getHour() <= 13) {

                                String iconCode = oneDayOnList.get("weather").get(0).get("icon").asString();
                                String temperature = oneDayOnList.get("main").get("temp").asString() + " °C";
                                SingleDayWeatherForecast singleDayWeatherForecast = new SingleDayWeatherForecast(localDate,iconCode,temperature);
                                weatherForecast.addWeatherForecastEntry(singleDayWeatherForecast);
                            }
                        }

                        return weatherForecast;
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
     *
     * @param location - (String) name of a city to check weather.
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}
