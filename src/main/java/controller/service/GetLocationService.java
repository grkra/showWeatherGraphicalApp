package controller.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.Location;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

// ERROR: 1. click on the button statrts service but does NOT trigger onSucceed()
/**
 * Controller responsible for getting current location.
 * It sends request to IP geolocation API to get current location of a device.
 */
public class GetLocationService extends Service<Location> {

    @Override
    protected Task<Location> createTask() {
        return new Task<Location>() {
            @Override
            protected Location call() throws Exception {

                System.out.println("Starting");
                HttpClient httpClient = HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(5))
                        .build();
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create("http://ip-api.com/json/?fields=status,message,city,lat,lon"))
                        .timeout(Duration.ofSeconds(5))
                        .GET()
                        .build();

                try {
                    System.out.println("Inside try");
                    HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                    int httpResponseStatusCode = httpResponse.statusCode();

                    System.out.println("HTTP Response code: " + httpResponseStatusCode);
                    if (httpResponseStatusCode >=200 && httpResponseStatusCode <300) {
                        System.out.println("HTTP Response body: " + httpResponse.body());
                        ObjectMapper jsonMapper = new ObjectMapper();
                        JsonNode jsonRoot = jsonMapper.readTree(httpResponse.body());
                        String cityName = jsonRoot.get("city").asString();
                        String cityLongitude = jsonRoot.get("lon").asString();
                        String cityLatitude = jsonRoot.get("lat").asString();

                        return new Location(cityName, cityLongitude, cityLatitude);
                    } else {
                        throw new IOException("HTTP Error: " + httpResponseStatusCode);
                    }
                } catch (Exception e) {
                    throw new IOException("Connection error", e);
                }
            }
        };
    }
}
