package krawczyk.grzegorz.controller.service.client;

import krawczyk.grzegorz.model.Location;

import java.io.IOException;

/**
 * Interface used as abstraction layer between GetLocationService and concrete GetLocationAPIClient.
 * All GetLocationAPIClients should implement this interface.
 * <p>
 * GetLocationAPIClient sends request to IP geolocation API to get location.
 * API must provide current location of a device (city name, latitude and longitude).
 */
public interface GetLocationAPIClient {


    /**
     * Method is used to send request to API providing current location of a device.
     * API must be able to send back Location objects:
     * - name of a city,
     * - latitude,
     * - longitude.
     *
     * @return new Location object.
     * @throws IOException
     */
    Location getLocation() throws IOException;
}
