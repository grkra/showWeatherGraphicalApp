package krawczyk.grzegorz.controller.service;

import krawczyk.grzegorz.controller.service.client.GetLocationAPIClient;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import krawczyk.grzegorz.model.Location;

/**
 * Controller responsible for getting current location.
 * It sends request to IP geolocation API to get current location of a device.
 */
public class GetLocationService extends Service<Location> {

    private final GetLocationAPIClient getLocationAPIClient;

    /**
     * Constructor of the GetLocationService service.
     *
     * @param getLocationAPIClient Concrete class implementing GetLocationAPIClient interface used to send request to API returning current location.
     */
    GetLocationService(GetLocationAPIClient getLocationAPIClient) {
        this.getLocationAPIClient = getLocationAPIClient;
    }

    @Override
    protected Task<Location> createTask() {
        return new Task<Location>() {
            @Override
            protected Location call() throws Exception {

                return getLocationAPIClient.getLocation();
            }
        };
    }
}
