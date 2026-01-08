package controller.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Controller responsible for getting current location.
 * It sends request to IP geolocation API to get current location of a device.
 */
public class GetCurrentLocationService extends Service<String> {

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                HttpClient httpClient = HttpClient.newBuilder().build();
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create("http://ip-api.com/json"))
                        .GET()
                        .build();

                HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

                return httpResponse.body();
            }
        };
    }
}
