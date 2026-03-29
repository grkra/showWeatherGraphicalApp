package krawczyk.grzegorz.controller;

import krawczyk.grzegorz.WeatherManager;

/**
 * Base class to be extended in all controllers.
 */
public abstract class BaseController {

    protected WeatherManager weatherManager;
    private String fxmlFile;

    /**
     * Constructor of the BaseController class.
     * @param weatherManager (WeatherManager) - object used to handle state of an application.
     * @param fxmlFile (String) - name of the fxml file.
     */
    public BaseController(WeatherManager weatherManager, String fxmlFile) {
        this.weatherManager = weatherManager;
        this.fxmlFile = fxmlFile;
    }

    /**
     * Method returns name of the fxml file connected with the controller.
     * @return (String) name of the fxml file.
     */
    public String getFxmlName() {
        return fxmlFile;
    };
}
