package controller;

import controller.service.GetCurrentWeatherService;
import controller.service.GetLocationService;
import controller.service.GetWeatherForecastService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Location;
import model.CurrentWeather;
import model.WeatherForecast;

import java.net.URL;
import java.time.LocalDate;
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

    @FXML
    private GridPane weatherForecastGrid;

    @FXML
    private Label weatherForecastLabel;

    @FXML
    private Label weatherForecastLabel01;

    @FXML
    private Label weatherForecastLabel02;

    @FXML
    private Label weatherForecastLabel10;

    @FXML
    private Label weatherForecastLabel20;

    @FXML
    private Label weatherForecastLabel30;

    @FXML
    private Label weatherForecastLabel40;

    @FXML
    private Label weatherForecastLabel50;

    @FXML
    private VBox weatherForecastVbox11;

    @FXML
    private VBox weatherForecastVbox12;

    @FXML
    private VBox weatherForecastVbox21;

    @FXML
    private VBox weatherForecastVbox22;

    @FXML
    private VBox weatherForecastVbox31;

    @FXML
    private VBox weatherForecastVbox32;

    @FXML
    private VBox weatherForecastVbox41;

    @FXML
    private VBox weatherForecastVbox42;

    @FXML
    private VBox weatherForecastVbox51;

    @FXML
    private VBox weatherForecastVbox52;

    private Node[][] weatherForecastGridElements;
    private Location currentLocation;
    private Location targetLocation;
    private CurrentWeather currentWeather;
    private WeatherForecast weatherForecast;
    private LocalDate[] weatherForecastDates;

    public MainWindowController() {
        this.weatherForecastDates = new LocalDate[5];
        for (int i = 0; i < 5; i++) {
            this.weatherForecastDates[i] = LocalDate.now().plusDays(1 + i);
        }
    }

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
        // Weather forecast grid:
        this.weatherForecastGridElements = new Node[][]{
                {null, this.weatherForecastLabel10, this.weatherForecastLabel20, this.weatherForecastLabel30, this.weatherForecastLabel40, this.weatherForecastLabel50},
                {this.weatherForecastLabel01, this.weatherForecastVbox11, this.weatherForecastVbox21, this.weatherForecastVbox31, this.weatherForecastVbox41, this.weatherForecastVbox51},
                {this.weatherForecastLabel02, this.weatherForecastVbox12, this.weatherForecastVbox22, this.weatherForecastVbox32, this.weatherForecastVbox42, this.weatherForecastVbox52}
        };

        // Location objects
        this.currentLocation = new Location();

        // LocationService
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

        // GetCurrentWeatherService
        this.currentWeatherService = new GetCurrentWeatherService();
        this.currentWeatherService.setOnSucceeded(
                workerStateEvent -> {
                    this.currentWeather = this.currentWeatherService.getValue().getWeather();
                    this.currentLocation = this.currentWeatherService.getValue().getLocation();

                    this.currentLocationIcon.setImage(new Image("/icons/" + this.currentWeather.getIconCode() + ".png"));
                    this.currentLocationDescriptionLable.setText(this.currentWeather.getDescription());
                    this.currentLocationTemperatureLabel.setText("Temperature: " + this.currentWeather.getTemperature());
                    this.currentLocationFeelsLikeTemperatureLabel.setText("Feels like: " + this.currentWeather.getFeelsLikeTemperature());
                    this.currentLocationWindSpeedLabel.setText("Wind speed: " + this.currentWeather.getWindSpeed());
                    this.currentLocationCloudinessLabel.setText("Cloudiness: " + this.currentWeather.getCloudiness());
                    this.currentLocationHumidityLabel.setText("Humidity: " + this.currentWeather.getHumidity());
                    this.currentLocationPressureLabel.setText("Pressure: " + this.currentWeather.getPressure());

                    this.currentLocationErrorLabel.setText("");
                });
        this.currentWeatherService.setOnFailed(
                workerStateEvent ->
                {
                    this.currentLocationErrorLabel.setText("Couldn't check weather. Please try later.");
                });

        // GetWeatherForecastService
        this.weatherForecastService = new GetWeatherForecastService();
        this.weatherForecastService.setOnSucceeded(workerStateEvent ->
        {
            this.weatherForecastLabel.setText("Weather forecast");

            this.weatherForecast = this.weatherForecastService.getValue();

            ((Label) this.weatherForecastGridElements[1][0]).setText(this.weatherForecast.getCityname());
            for (int i = 0; i < this.weatherForecast.getWeatherForecastEntries().size(); i++) {
                // Set 1. line - days
                ((Label) this.weatherForecastGridElements[0][i + 1]).setText(setDayAlias(this.weatherForecastDates[i]));

                // Set 2. line - current location weather forecast
                if (this.weatherForecastDates[i].equals(this.weatherForecast.getWeatherForecastEntries().get(i).getLocalDate())) {
                    ((ImageView) ((VBox) this.weatherForecastGridElements[1][i + 1]).getChildren().getFirst()).setImage((new Image("/icons/" + this.weatherForecast.getWeatherForecastEntries().get(i).getIconCode() + ".png")));
                    ((Label) ((VBox) this.weatherForecastGridElements[1][i + 1]).getChildren().getLast()).setText(this.weatherForecast.getWeatherForecastEntries().get(i).getFeelsLikeTemperature());
                }

            }
        });
    }

    private String setDayAlias(LocalDate weatherForecastDate) {
        LocalDate today = LocalDate.now();
        if (weatherForecastDate.equals(today.plusDays(1))) {
            return "Tomorrow";
        } else {
            String dayOfWeek = weatherForecastDate.getDayOfWeek().toString();
            return dayOfWeek.charAt(0) + dayOfWeek.substring(1).toLowerCase();
        }
    }
}
