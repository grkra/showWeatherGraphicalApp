package controller;

import controller.service.GetLocationService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of the main window of the application.
 */
public class MainWindowController implements Initializable {
    @FXML
    private TextField currentLocalizationField;

    /**
     * Object of the GetLocationService class.
     * It's service used to get geolocation of a device on which the application works.
     * It is used when GetCurrentLocation button is clicked.
     * It is initialized in initialize() method.
     */
    GetLocationService locationService;

    /**
     * Event listener triggered by clicking on check current localization button.
     * It is used to get current localization of a device and to add this localization
     * to input window.
     */
    @FXML
    void checkCurrentLocalizationButtonAction() {
        this.locationService.restart();
    }

    /**
     * Event listener triggered by clicking on check weather for current localization button.
     */
    @FXML
    void checkCurrentWeatherButtonAction() {
        System.out.println("Clicked check weather for current localization");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // LocationService initialization:
        this.locationService = new GetLocationService();
        this.locationService.setOnSucceeded(
                workerStateEvent -> {
                    this.currentLocalizationField.setText(locationService.getValue().getName());
                }
        );
        this.locationService.setOnFailed(
                workerStateEvent ->
                {
                    System.out.println(locationService.getException());
                });

    }
}
