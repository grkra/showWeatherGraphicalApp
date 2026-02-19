package controller;

import controller.service.GetWeatherService;
import controller.service.GetLocationService;
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
import model.WeatherData;
import model.WeatherForecast;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Controller of the main window of the application.
 */
public class MainWindowController implements Initializable {
    @FXML
    private Label errorLabel;

    @FXML
    private Label currentLocationCloudinessLabel;

    @FXML
    private Label currentLocationDescriptionLable;

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
    private Label destinationCloudinessLabel;

    @FXML
    private Label destinationDescriptionLable;

    @FXML
    private Label destinationFeelsLikeTemperatureLabel;

    @FXML
    private TextField destinationField;

    @FXML
    private Label destinationHumidityLabel;

    @FXML
    private ImageView destinationIcon;

    @FXML
    private Label destinationPressureLabel;

    @FXML
    private Label destinationTemperatureLabel;

    @FXML
    private Label destinationWindSpeedLabel;

    @FXML
    private GridPane weatherForecastGrid;

    @FXML
    private VBox weatherForecastSection;

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
    private WeatherData currentLocationWeather;
    private WeatherData destinationWeather;
    private LocalDate[] weatherForecastDates;

    public MainWindowController() {
        // initialize WeatherData objects
        this.currentLocationWeather = new WeatherData(new Location(true));
        this.destinationWeather = new WeatherData(new Location(false));

        // initialize array of 5 days starting tomorrow (to be displayed in weather forecast section)
        this.weatherForecastDates = new LocalDate[5];
        for (int i = 0; i < 5; i++) {
            this.weatherForecastDates[i] = LocalDate.now().plusDays(1 + i);
        }

        // initialize services
        this.locationService = new GetLocationService();
        this.weatherService = new GetWeatherService();
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
    GetWeatherService weatherService;

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
    void checkCurrentLocationWeatherButtonAction() {
        if (this.checkIfFieldNotBlank(this.currentLocationField.getText())) {
            setLocation(this.currentLocationField.getText(), this.currentLocationWeather.getLocation());

            this.weatherService.setLocation(this.currentLocationWeather.getLocation());
            this.weatherService.restart();
        }
    }

    @FXML
    void checkDestinationWeatherButtonAction() {

        if (this.checkIfFieldNotBlank(this.destinationField.getText())) {

            setLocation(this.destinationField.getText(), this.destinationWeather.getLocation());
            this.weatherService.setLocation(this.destinationWeather.getLocation());
            this.weatherService.restart();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Weather forecast grid:
        this.weatherForecastGridElements = new Node[][]{
                {null, this.weatherForecastLabel10, this.weatherForecastLabel20, this.weatherForecastLabel30, this.weatherForecastLabel40, this.weatherForecastLabel50},
                {this.weatherForecastLabel01, this.weatherForecastVbox11, this.weatherForecastVbox21, this.weatherForecastVbox31, this.weatherForecastVbox41, this.weatherForecastVbox51},
                {this.weatherForecastLabel02, this.weatherForecastVbox12, this.weatherForecastVbox22, this.weatherForecastVbox32, this.weatherForecastVbox42, this.weatherForecastVbox52}
        };

        // Set 1. line of weather forecast grig - dates:
        for (int i = 0; i < 5; i++) {
            ((Label) this.weatherForecastGridElements[0][i + 1]).setText(setDayAlias(this.weatherForecastDates[i]));
        }

        // GetLocationService
        this.locationService.setOnSucceeded(
                workerStateEvent -> {
                    this.currentLocationWeather.setLocation(this.locationService.getValue());
                    this.currentLocationField.setText(this.currentLocationWeather.getLocation().getName());
                    this.errorLabel.setText("");
                }
        );
        this.locationService.setOnFailed(
                workerStateEvent ->
                {
                    this.errorLabel.setText("Couldn't check current location. Please try later or type it.");
                });

        // GetWeatherService
        this.weatherService.setOnSucceeded(
                workerStateEvent -> {
                    WeatherData weatherData = this.weatherService.getValue();

                    // Current weather section
                    if (weatherData.getLocation().getIsCurrentLocation()) {
                        this.currentLocationWeather=weatherData;

                        this.currentLocationIcon.setImage(new Image("/icons/" + this.currentLocationWeather.getCurrentWeather().getIconCode() + ".png"));
                        this.currentLocationDescriptionLable.setText(this.currentLocationWeather.getCurrentWeather().getDescription());
                        this.currentLocationTemperatureLabel.setText("Temperature: " + this.currentLocationWeather.getCurrentWeather().getTemperature());
                        this.currentLocationFeelsLikeTemperatureLabel.setText("Feels like: " + this.currentLocationWeather.getCurrentWeather().getFeelsLikeTemperature());
                        this.currentLocationWindSpeedLabel.setText("Wind speed: " + this.currentLocationWeather.getCurrentWeather().getWindSpeed());
                        this.currentLocationCloudinessLabel.setText("Cloudiness: " + this.currentLocationWeather.getCurrentWeather().getCloudiness());
                        this.currentLocationHumidityLabel.setText("Humidity: " + this.currentLocationWeather.getCurrentWeather().getHumidity());
                        this.currentLocationPressureLabel.setText("Pressure: " + this.currentLocationWeather.getCurrentWeather().getPressure());

                        this.errorLabel.setText("");
                    } else {
                        this.destinationWeather=weatherData;

                        this.destinationIcon.setImage(new Image("/icons/" + this.destinationWeather.getCurrentWeather().getIconCode() + ".png"));
                        this.destinationDescriptionLable.setText(this.destinationWeather.getCurrentWeather().getDescription());
                        this.destinationTemperatureLabel.setText("Temperature: " + this.destinationWeather.getCurrentWeather().getTemperature());
                        this.destinationFeelsLikeTemperatureLabel.setText("Feels like: " + this.destinationWeather.getCurrentWeather().getFeelsLikeTemperature());
                        this.destinationWindSpeedLabel.setText("Wind speed: " + this.destinationWeather.getCurrentWeather().getWindSpeed());
                        this.destinationCloudinessLabel.setText("Cloudiness: " + this.destinationWeather.getCurrentWeather().getCloudiness());
                        this.destinationHumidityLabel.setText("Humidity: " + this.destinationWeather.getCurrentWeather().getHumidity());
                        this.destinationPressureLabel.setText("Pressure: " + this.destinationWeather.getCurrentWeather().getPressure());

                        this.errorLabel.setText("");
                    }

                    // Weather forecast section
                    this.weatherForecastSection.setVisible(true);

                    if (weatherData.getLocation().getIsCurrentLocation()) {
                        ((Label) this.weatherForecastGridElements[1][0]).setText(this.currentLocationWeather.getLocation().getName());
                        for (int i = 0; i < this.currentLocationWeather.getWeatherForecast().getWeatherForecastEntries().size(); i++) {

                            // Set 2. line - current location weather forecast
                            if (this.weatherForecastDates[i].equals(this.currentLocationWeather.getWeatherForecast().getWeatherForecastEntries().get(i).getLocalDate())) {
                                ((ImageView) ((VBox) this.weatherForecastGridElements[1][i + 1]).getChildren().getFirst()).setImage((new Image("/icons/" + this.currentLocationWeather.getWeatherForecast().getWeatherForecastEntries().get(i).getIconCode() + ".png")));
                                ((Label) ((VBox) this.weatherForecastGridElements[1][i + 1]).getChildren().getLast()).setText(this.currentLocationWeather.getWeatherForecast().getWeatherForecastEntries().get(i).getFeelsLikeTemperature());
                            }
                        }
                    } else {
                        ((Label) this.weatherForecastGridElements[2][0]).setText(this.destinationWeather.getLocation().getName());
                        for (int i = 0; i < this.destinationWeather.getWeatherForecast().getWeatherForecastEntries().size(); i++) {

                            // Set 3. line - destination weather forecast
                            if (this.weatherForecastDates[i].equals(this.destinationWeather.getWeatherForecast().getWeatherForecastEntries().get(i).getLocalDate())) {
                                ((ImageView) ((VBox) this.weatherForecastGridElements[2][i + 1]).getChildren().getFirst()).setImage((new Image("/icons/" + this.destinationWeather.getWeatherForecast().getWeatherForecastEntries().get(i).getIconCode() + ".png")));
                                ((Label) ((VBox) this.weatherForecastGridElements[2][i + 1]).getChildren().getLast()).setText(this.destinationWeather.getWeatherForecast().getWeatherForecastEntries().get(i).getFeelsLikeTemperature());
                            }

                        }
                    }
                });
        this.weatherService.setOnFailed(
                workerStateEvent ->
                {
                    this.errorLabel.setText("Couldn't check weather. Please try later.");
                });
    }

    /**
     * Method transforms date (LocalDate object) to name: tomorrow or name of a day
     * Method is used to display names of days in weather forecast section.
     * @param weatherForecastDate (LocalDate) date to be displayed in weather forecast section
     * @return (String) name of a day
     */
    private String setDayAlias(LocalDate weatherForecastDate) {
        LocalDate today = LocalDate.now();
        if (weatherForecastDate.equals(today.plusDays(1))) {
            return "Tomorrow";
        } else {
            String dayOfWeek = weatherForecastDate.getDayOfWeek().toString();
            return dayOfWeek.charAt(0) + dayOfWeek.substring(1).toLowerCase();
        }
    }

    /**
     * Method checks if field to type location isn't empty or blank.
     * @param typedCityName (String) - value typed in TextField
     * @return fale - if text field was blank or empty, true otherwise
     */
    private boolean checkIfFieldNotBlank(String typedCityName) {
        if (typedCityName.isBlank()) {
            return false;
        }
        return true;
    }

    /**
     * Method checks if city name typed in text field is different from this saved in location object.
     * If it is different, that means user typed new (different) city.
     * In this case method clears longitude and latitude in the location object
     * so getWeather services use city name to get data (and not longitude and latitude).
     * @param typedCityName (String) city name typed in text field
     * @param locationObject (Location) currentLocation or destinationLocation
     */
    private void setLocation(String typedCityName, Location locationObject) {
        if (!typedCityName.equalsIgnoreCase(locationObject.getName())) {
            locationObject.setName(typedCityName);
            locationObject.setLongitude("");
            locationObject.setLatitude("");
        }
    }
}
