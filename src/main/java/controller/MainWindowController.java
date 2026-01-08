package controller;

import controller.service.GetCurrentLocationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.http.HttpClient;

/**
 * Controller of the main window of the application.
 */
public class MainWindowController {
    @FXML
    private TextField currentLocalizationField;

    /**
     * Event listener triggered by clicking on check current localization button.
     * It is used to get current localization of a device and to add this localization
     * to input window.
     */
    @FXML
    void checkCurrentLocalizationButtonAction() {
        GetCurrentLocationService locationService = new GetCurrentLocationService();

        locationService.setOnSucceeded(
                workerStateEvent -> {
                    System.out.println(locationService.getValue());
                }
        );

        locationService.start();
    }

    /**
     * Event listener triggered by clicking on check weather for current localization button.
     */
    @FXML
    void checkCurrentWeatherButtonAction() {
        System.out.println("Clicked check weather for current localization");
    }
}
