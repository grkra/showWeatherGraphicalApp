package controller;

import controller.service.GetCurrentWeatherService;
import controller.service.GetLocationService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Location;
import model.Weather;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of the main window of the application.
 */
public class MainWindowController implements Initializable {
    @FXML
    private Label currentLocationCloudinessLabel;

    @FXML
    private Label currentLocationDescriptionLable;

    @FXML
    private Label currentLocationErrorLabel;

    @FXML
    private Label currentLocationFeelsLikeTemperatureLabel;

    @FXML
    private TextField currentLocationField;

    @FXML
    private Label currentLocationHumidityLabel;

    @FXML
    private ImageView currentLocationIcon;

    @FXML
    private Label currentLocationPressureLabel;

    @FXML
    private Label currentLocationTemperatureLabel;

    @FXML
    private Label currentLocationWindSpeedLabel;

    @FXML
    private TextField destinationLocationField;

    private Location currentLocation;
    private Location targetLocation;
    private Weather currentWeatherCurrentLocation;

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
        if (this.currentLocation.getName().isBlank()
                ||
                !this.currentLocationField.getText().toLowerCase().equals(this.currentLocation.getName().toLowerCase())) {
            this.currentLocation.setLocation(this.currentLocationField.getText(), "", "");
        }

        this.currentWeatherService.setLocation(this.currentLocation);
        this.currentWeatherService.restart();
    }

    @FXML
    void checkDestinationWeatherButtonAction() {
        System.out.println("clicked destination check weather");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Location objects
        this.currentLocation = new Location();

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
        this.currentWeatherService.setOnSucceeded(
                workerStateEvent -> {
                    this.currentWeatherCurrentLocation = this.currentWeatherService.getValue().getWeather();
                    this.currentLocation = this.currentWeatherService.getValue().getLocation();

                    this.currentLocationIcon.setImage(new Image("/icons/"+this.currentWeatherCurrentLocation.getIconCode() + ".png"));
                    this.currentLocationDescriptionLable.setText(this.currentWeatherCurrentLocation.getDescription());
                    this.currentLocationTemperatureLabel.setText("Temperature: " + this.currentWeatherCurrentLocation.getTemperature());
                    this.currentLocationFeelsLikeTemperatureLabel.setText("Feels like: " + this.currentWeatherCurrentLocation.getFeelsLikeTemperature());
                    this.currentLocationWindSpeedLabel.setText("Wind speed: " + this.currentWeatherCurrentLocation.getWindSpeed());
                    this.currentLocationCloudinessLabel.setText("Cloudiness: " + this.currentWeatherCurrentLocation.getCloudiness());
                    this.currentLocationHumidityLabel.setText("Humidity: " + this.currentWeatherCurrentLocation.getHumidity());
                    this.currentLocationPressureLabel.setText("Pressure: " + this.currentWeatherCurrentLocation.getPressure());

                    this.currentLocationErrorLabel.setText("");
                });
        this.currentWeatherService.setOnFailed(
                workerStateEvent ->
                {
                    this.currentLocationErrorLabel.setText("Couldn't check weather. Please try later.");
                });
    }
}
