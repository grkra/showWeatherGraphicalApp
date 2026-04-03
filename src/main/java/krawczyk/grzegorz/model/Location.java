package krawczyk.grzegorz.model;

import java.io.Serializable;

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

    public String getName() {
        return name;
    }

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

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
