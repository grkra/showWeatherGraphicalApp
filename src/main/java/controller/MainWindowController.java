package controller;

import controller.service.GetCurrentWeatherService;
import controller.service.GetLocationService;
import controller.service.GetWeatherForecastService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Location;
import model.CurrentWeather;
import model.WeatherForecast;

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
    private CurrentWeather currentCurrentWeatherCurrentLocation;
    private WeatherForecast weatherForecast;

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
     * Object of the GetWeatherForecastService class.
     * It's service used to get 7-days weather forecast for specified location.
     * It is used when GetWeather button is clicked.
     * It is initialized in initialize() method.
     */
    GetWeatherForecastService weatherForecastService;

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
        // Longitude and latitude are saved to currentLocation if a user clicked getLocation button (from GetLocationService)
        // or already clicked getWeather button (from GetCurrentWeatherService).
        // But if a user changed city after that, longitude and latitude need to be cleared,
        // so getWeatherServices should send request with city name instead of geographical coordinates.
        if (this.currentLocation.getName().isBlank()
                ||
                !this.currentLocationField.getText().toLowerCase().equals(this.currentLocation.getName().toLowerCase())) {
            this.currentLocation.setLocation(this.currentLocationField.getText(), "", "");
        }

        this.currentWeatherService.setLocation(this.currentLocation);
        this.currentWeatherService.restart();
        this.weatherForecastService.setLocation(this.currentLocation);
        this.weatherForecastService.restart();
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
                    this.currentCurrentWeatherCurrentLocation = this.currentWeatherService.getValue().getWeather();
                    this.currentLocation = this.currentWeatherService.getValue().getLocation();

                    this.currentLocationIcon.setImage(new Image("/icons/" + this.currentCurrentWeatherCurrentLocation.getIconCode() + ".png"));
                    this.currentLocationDescriptionLable.setText(this.currentCurrentWeatherCurrentLocation.getDescription());
                    this.currentLocationTemperatureLabel.setText("Temperature: " + this.currentCurrentWeatherCurrentLocation.getTemperature());
                    this.currentLocationFeelsLikeTemperatureLabel.setText("Feels like: " + this.currentCurrentWeatherCurrentLocation.getFeelsLikeTemperature());
                    this.currentLocationWindSpeedLabel.setText("Wind speed: " + this.currentCurrentWeatherCurrentLocation.getWindSpeed());
                    this.currentLocationCloudinessLabel.setText("Cloudiness: " + this.currentCurrentWeatherCurrentLocation.getCloudiness());
                    this.currentLocationHumidityLabel.setText("Humidity: " + this.currentCurrentWeatherCurrentLocation.getHumidity());
                    this.currentLocationPressureLabel.setText("Pressure: " + this.currentCurrentWeatherCurrentLocation.getPressure());

                    this.currentLocationErrorLabel.setText("");
                });
        this.currentWeatherService.setOnFailed(
                workerStateEvent ->
                {
                    this.currentLocationErrorLabel.setText("Couldn't check weather. Please try later.");
                });

        // GetWeatherForecastService initialization:
        this.weatherForecastService = new GetWeatherForecastService();
        this.weatherForecastService.setOnSucceeded(workerStateEvent ->
        {
            this.weatherForecast = this.weatherForecastService.getValue();
            System.out.println(this.weatherForecast);
        });
    }
}
