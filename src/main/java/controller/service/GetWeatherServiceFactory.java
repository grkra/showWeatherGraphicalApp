package controller.service;

import controller.service.client.OpenWeatherMapWeatherAPIClient;

/**
 * Factory responsible for creating GetWeatherService object.
 */
public class GetWeatherServiceFactory {

    /**
     * Method returns new object of class GetWeatherService.
     * @return (GetWeatherService) new weather service.
     */
    public static GetWeatherService createGetWeatherService () {
        return new GetWeatherService(new OpenWeatherMapWeatherAPIClient());
    }
}
