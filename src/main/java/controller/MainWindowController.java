package controller;

import controller.service.GetLocationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller of the main window of the application.
 */
public class MainWindowController {
    @FXML
    private TextField currentLocalizationField;

    // ERROR: 1. click on the button statrts service but does NOT trigger onSucceed()
    /**
     * Event listener triggered by clicking on check current localization button.
     * It is used to get current localization of a device and to add this localization
     * to input window.
     */
    @FXML
    void checkCurrentLocalizationButtonAction() {
        System.out.println("Clicked check location");
        GetLocationService locationService = new GetLocationService();

        // ERROR: 1. click on the button statrts service but does NOT trigger onSucceed()
        locationService.setOnSucceeded(
                workerStateEvent -> {
                    this.currentLocalizationField.setText(locationService.getValue().getName());
                }
        );
        locationService.setOnFailed(
                workerStateEvent ->
                {
                    System.out.println(locationService.getException());
                });

        locationService.restart();
        System.out.println("Started");
    }

    /**
     * Event listener triggered by clicking on check weather for current localization button.
     */
    @FXML
    void checkCurrentWeatherButtonAction() {
        System.out.println("Clicked check weather for current localization");
    }
}
