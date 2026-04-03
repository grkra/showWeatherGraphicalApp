package krawczyk.grzegorz.controller.service.client;

import krawczyk.grzegorz.model.Location;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Controller responsible for getting geographical location data from ip-api.
 * It sends request to ip-api to get location (city name, latitude, longitude).
 */
public class IpApiLocationAPIClient implements GetLocationAPIClient {

    /**
     * Connection object used to send HTTP request and get responses from API.
     */
    private HttpClient httpClient;

    /**
     * Constructor of the IpApiLocationAPIClient API connector.
     * It initializes http client object for the requests to API.
     */
    public IpApiLocationAPIClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    /**
     * Method responsible for sending requests for current location.
     * @return (Location) - city for which weather is being checked.
     * @throws IOException - in case of connection error.
     */
    public Location getLocation () throws IOException {

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://ip-api.com/json/?fields=status,message,city,lat,lon"))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            int httpResponseStatusCode = httpResponse.statusCode();

            if (httpResponseStatusCode >=200 && httpResponseStatusCode <300) {
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
}
