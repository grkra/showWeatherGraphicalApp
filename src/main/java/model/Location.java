package model;

/**
 * City - name of the city, longitude, latitude
 */
public class Location {
    private String name;
    private String longitude;
    private String latitude;

    /**
     * Constructor of the class Location.
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
}
