package krawczyk.grzegorz.controller.persistence;

import krawczyk.grzegorz.WeatherManager;
import krawczyk.grzegorz.model.Location;
import krawczyk.grzegorz.model.WeatherData;

import java.io.*;

public class PersistenceAccess {

    /**
     * Path to store date
     */
    private String VALID_ACCOUNTS_LOCATION = System.getProperty("user.home") + File.separator + "weatherData.ser";

    public WeatherManager loadFromFile() {

        WeatherManager weatherManager = new WeatherManager(new WeatherData(new Location()), new WeatherData(new Location()));

        try {
            FileInputStream fileInputStream = new FileInputStream(this.VALID_ACCOUNTS_LOCATION);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            weatherManager = (WeatherManager) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherManager;
    }

    public void saveToFile(WeatherManager weatherDataToPersist) {

        System.out.println("ZAPISYWANIE");
        System.out.println(weatherDataToPersist);
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
