package model;

/**
 * City - name of the city, longitude, latitude
 */
public class Location {
    private String name = "";
    private String longitude ="";
    private String latitude = "";

    /**
     * Constructor of the class Location with no parameters.
     * Name, latitude and longitude need to be set with setters.
     */
    public Location() {}

    /**
     * Constructor of the class Location with name of a city, itt latitude and longitude.
     * Constructor initializes all properties.
     * @param name (String) name of a city in English
     * @param longitude (String) geographical longitude of a city
     * @param latitude (String) geographical latitude of a city
     */
    public Location(String name, String longitude, String latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setLocation (String name, String longitude, String latitude) {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
        this.longitude = longitude;
        this.latitude = latitude;
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
