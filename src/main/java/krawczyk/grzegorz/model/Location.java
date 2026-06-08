package krawczyk.grzegorz.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * City - name of a city, longitude, latitude and information if it is currentLocation
 */
public class Location implements Serializable {
    private String name = "";
    private String longitude = "";
    private String latitude = "";

    /**
     * Empty constructor of the class Location.
     */
    public Location() {
    }

    /**
     * Constructor of the class Location with name of a city, its latitude and longitude.
     * Constructor initializes all properties.
     *
     * @param name              (String) name of a city in English
     * @param longitude         (String) geographical longitude of a city
     * @param latitude          (String) geographical latitude of a city
     */
    public Location(String name, String longitude, String latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Returns city name.
     * @return (String) city name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets city name. Method changes firs letter of every word in city name to capital letter.
     * @param name (String) city name.
     */
    public void setName(String name) {
        StringBuilder stringBuilder = new StringBuilder(name.length());
        boolean isNewWord = true;

        for (char character : name.toCharArray()) {
            if (Character.isLetter(character)) {
                if (isNewWord) {
                    character = Character.toUpperCase(character);
                    isNewWord = false;
                }
            } else {
                isNewWord = true;
            }
            stringBuilder.append(character);
        }

        this.name = stringBuilder.toString();
    }

    /**
     * Returns longitude.
     * @return (String) longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     * @param longitude (String) longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Returns latitude.
     * @return (String) latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     * @param latitude (String) latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(name, location.name) && Objects.equals(longitude, location.longitude) && Objects.equals(latitude, location.latitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, longitude, latitude);
    }
}
