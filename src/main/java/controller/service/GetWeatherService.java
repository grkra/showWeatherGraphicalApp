package controller.service;

import controller.service.client.GetWeatherAPIClient;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.Location;
import model.WeatherData;

/**
 * Controller responsible for getting weather data for the passed location.
 * It is used as additional layer of abstraction between MainWindowController and actual Client.
 * It calls Client object to get weather forecast.
 */
public class GetWeatherService extends Service<WeatherData> {

    private final GetWeatherAPIClient getWeatherAPIClient;
    private Location location;

    /**
     * Constructor of the class GetWeatherService.
     *
     * @param getWeatherAPIClient Concrete class implementing GetWeatherAPIClient interface used to send request to API returning weather forecast.
     */
    GetWeatherService(GetWeatherAPIClient getWeatherAPIClient) {
        this.getWeatherAPIClient = getWeatherAPIClient;
    }

    @Override
    protected Task<WeatherData> createTask() {
        return new Task<WeatherData>() {
            @Override
            protected WeatherData call() throws Exception {

                return getWeatherAPIClient.getWeather();
            }
        };
    }

    /**
     * Method passes location to GetWeatherAPIClient object (client used in the service).
     *
     * @param location - (Location) city to check weather.
     */
    public void setLocation(Location location) {
        this.getWeatherAPIClient.setLocation(location);
    }
}
