package controller;

import controller.service.GetCurrentWeatherService;
import controller.service.GetLocationService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Location;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of the main window of the application.
 */
public class MainWindowController implements Initializable {
    @FXML
    private Label currentLocationErrorLabel;

    @FXML
    private TextField currentLocationField;

    @FXML
    private TextField destinationLocationField;

    private Location currentLocation;
    private Location targetLocation;

    /**
     * Object of the GetLocationService class.
     * It's service used to get geolocation of a device on which the application works.
     * It is used when GetCurrentLocation button is clicked.
     * It is initialized in initialize() method.
     */
    GetLocationService locationService;

    /**
     * Object of the GetCurrentWeatherService class.
     * It's service used to get current weather for specified location.
     * It is used when GetWeather button is clicked.
     * It is initialized in initialize() method.
     */
    GetCurrentWeatherService currentWeatherService;

    /**
     * Event listener triggered by clicking on check current location button.
     * It is used to get current location of a device and to add this location
     * to input window.
     */
    @FXML
    void checkCurrentLocationButtonAction() {
        this.locationService.restart();
    }

    /**
     * Event listener triggered by clicking on check weather for current localization button.
     */
    @FXML
    void checkCurrentWeatherButtonAction() {
        this.currentWeatherService.restart();
    }

    @FXML
    void checkDestinationWeatherButtonAction() {
        System.out.println("clicked destination check weather");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // LocationService initialization:
        this.locationService = new GetLocationService();
        this.locationService.setOnSucceeded(
                workerStateEvent -> {
                    this.currentLocation = this.locationService.getValue();
                    this.currentWeatherService.setLocation(this.currentLocation);
                    this.currentLocationField.setText(this.currentLocation.getName());
                    this.currentLocationErrorLabel.setText("");
                }
        );
        this.locationService.setOnFailed(
                workerStateEvent ->
                {
                    this.currentLocationErrorLabel.setText("Couldn't check current location. Please try later or type it.");
                });


        // GetCurrentWeatherService initialization:
        this.currentWeatherService = new GetCurrentWeatherService();
        this.currentWeatherService.setOnSucceeded(workerStateEvent -> {
            System.out.println(this.currentWeatherService.getValue());
        });
    }
}
