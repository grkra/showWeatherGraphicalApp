package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainWindowController {
    @FXML
    private TextField currentLocalizationField;

    @FXML
    void checkCurrentLocalizationButtonAction() {
        System.out.println("Clicked check current localization");
    }

    @FXML
    void checkCurrentWeatherButtonAction() {
        System.out.println("Clicked check weather for current localization");
    }
}
