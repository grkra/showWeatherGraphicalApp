package krawczyk.grzegorz.controller.service;

import krawczyk.grzegorz.controller.service.client.GetWeatherAPIClient;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import krawczyk.grzegorz.model.Location;
import krawczyk.grzegorz.model.WeatherData;

/**
 * Controller responsible for getting weather data for the passed location.
 * It is used as additional layer of abstraction between MainWindowController and actual Client.
 * It calls Client object to get weather forecast.
 */
public class GetWeatherService extends Service<WeatherData> {

    private final GetWeatherAPIClient getWeatherAPIClient;
    private Location location;
    private boolean isCurrentLocation;

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

                return getWeatherAPIClient.getWeather(location, isCurrentLocation);
            }
        };
    }

    /**
     * Method sets location in GetWeatherService object.
     * Then Location is used to get weather from API.
     *
     * @param location - (Location) city to check weather.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Method sets isCurrentLocation in GetWeatherService object.
     * Then it is used to create returned WeatherData object.
     * @param currentLocation - (boolean) true if it is current location, false otherwise.
     */
    public void setIsCurrentLocation(boolean currentLocation) {
        isCurrentLocation = currentLocation;
    }
}
