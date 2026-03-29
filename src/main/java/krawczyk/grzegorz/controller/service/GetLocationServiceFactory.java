package krawczyk.grzegorz.controller.service;

import krawczyk.grzegorz.controller.service.client.IpApiLocationAPIClient;

/**
 * Factory responsible for creating GetLocationService object.
 */
public class GetLocationServiceFactory {

    /**
     * Method returns new object of class Location.
     * @return (Location) new location service.
     */
    public static GetLocationService createGetLocationService () {
        return new GetLocationService(new IpApiLocationAPIClient());
    }
}
