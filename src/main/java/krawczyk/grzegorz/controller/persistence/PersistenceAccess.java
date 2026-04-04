package krawczyk.grzegorz.controller.persistence;

import krawczyk.grzegorz.WeatherManager;
import krawczyk.grzegorz.model.Location;
import krawczyk.grzegorz.model.WeatherData;

import java.io.*;

/**
 * Class is responsible for saving WeatherManager to and loading from file
 */
public class PersistenceAccess {

    /**
     * Path to store date
     */
    private String VALID_ACCOUNTS_LOCATION = System.getProperty("user.home") + File.separator + "weatherData.ser";

    /**
     * Method reads data from persisted file and saves it to WeatherManager obejct.
     * @return WeatherManager object.
     * <ul>
     *     <li>If there was persisted file - it returns WeatherManager object with data from file</li>
     *     <li>If there was NO file - it returns new WeatherManager object with initialized Location property only</li>
     * </ul>
     *
     */
    public WeatherManager loadFromFile() {

        WeatherManager weatherManager = new WeatherManager(new WeatherData(new Location(), true), new WeatherData(new Location(), false));

        try {
            FileInputStream fileInputStream = new FileInputStream(this.VALID_ACCOUNTS_LOCATION);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            weatherManager = (WeatherManager) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            System.err.println("File not found. No data to load.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherManager;
    }

    /**
     * Method saves WeatherManager object to local file.
     * @param weatherDataToPersist (WeatherManager) - object containing weather data for current location and destination.
     */
    public void saveToFile(WeatherManager weatherDataToPersist) {

        try {
            File file = new File(this.VALID_ACCOUNTS_LOCATION);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(weatherDataToPersist);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
