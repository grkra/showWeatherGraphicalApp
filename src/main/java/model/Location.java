package model;

/**
 * City - name of the city, longitude, latitude
 */
public class Location {
    private String name = "";
    private String longitude ="";
    private String latitude = "";
    private boolean isCurrentLocation = true;

    /**
     * Constructor of the class Location. It needs to be set if this is current location or destination.
     * Name, latitude and longitude need to be set with setters.
     * @param isCurrentLocation (boolean) set to mark which location is current
     */
    public Location(boolean isCurrentLocation) {
        this.isCurrentLocation = isCurrentLocation;
    }

    /**
     * Constructor of the class Location with name of a city, itt latitude and longitude.
     * Constructor initializes all properties.
     * @param name (String) name of a city in English
     * @param longitude (String) geographical longitude of a city
     * @param latitude (String) geographical latitude of a city
     * @param isCurrentLocation (boolean) set to mark which location is current
     */
    public Location(String name, String longitude, String latitude, boolean isCurrentLocation) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isCurrentLocation = isCurrentLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public boolean getIsCurrentLocation() {
        return isCurrentLocation;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
