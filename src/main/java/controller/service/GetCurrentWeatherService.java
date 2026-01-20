package controller.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.Location;

import java.net.http.HttpClient;
import java.time.Duration;

/**
 * Controller responsible for getting current weather for location.
 * It sends request to OpenWeatherApi to get current weather.
 */
public class GetCurrentWeatherService extends Service<String> {

    private Location location;
    private HttpClient httpClient;

    public GetCurrentWeatherService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                return "25 st C in " + location.getName();
            }
        };
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
