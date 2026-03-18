package controller;

import controller.service.GetWeatherService;
import controller.service.GetLocationService;
import controller.service.GetWeatherServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Location;
import model.WeatherData;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Controller of the main window of the application.
 */
public class MainWindowController extends BaseController implements Initializable {
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
    private Label currentLocationLatitude;

    @FXML
    private Label currentLocationLongitude;

    @FXML
    private Label currentLocationName;

    @FXML
    private Label currentLocationPressureLabel;

    @FXML
    private Label currentLocationTemperatureLabel;

    @FXML
    private VBox currentLocationWeatherVbox;

    @FXML
    private Label currentLocationWindSpeedLabel;

    @FXML
    private Label destinationCloudinessLabel;

    @FXML
    private Label destinationDescriptionLabel;

    @FXML
    private Label destinationFeelsLikeTemperatureLabel;

    @FXML
    private TextField destinationField;

    @FXML
    private Label destinationHumidityLabel;

    @FXML
    private ImageView destinationIcon;

    @FXML
    private Label destinationLatitude;

    @FXML
    private Label destinationLongitude;

    @FXML
    private Label destinationName;

    @FXML
    private Label destinationPressureLabel;

    @FXML
    private Label destinationTemperatureLabel;

    @FXML
    private VBox destinationWeatherVbox;

    @FXML
    private Label destinationWindSpeedLabel;

    @FXML
    private Label errorLabel;

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
    private VBox weatherForecastSection;

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
     * Constructor of the class MainWindowController
     * @param fxmlName (String) - name of the fxml file connected with the controller.
     */
    public MainWindowController(String fxmlName) {
        super(fxmlName);
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
        this.weatherService = GetWeatherServiceFactory.createGetWeatherService();
    }

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
        if (this.validateField(this.currentLocationField)) {
            this.errorLabel.setText("");

            updateLocationIfNecessary(this.currentLocationField, this.currentLocationWeather.getLocation());

            this.weatherService.setLocation(this.currentLocationWeather.getLocation());
            this.weatherService.restart();
        } else {
            this.errorLabel.setText("Please fill the location first.");
        }
    }

    @FXML
    void checkDestinationWeatherButtonAction() {

        if (this.validateField(this.destinationField)) {
            this.errorLabel.setText("");

            updateLocationIfNecessary(this.destinationField, this.destinationWeather.getLocation());

            this.weatherService.setLocation(this.destinationWeather.getLocation());
            this.weatherService.restart();
        } else {
            this.errorLabel.setText("Please fill the location first.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initializes array with Weather forecast grid cells.
        // You can't iterate through Grid Pane from JavaFX - it's not an array.
        // To be able to easily set values to Grid cells, every cell is saved to this array.
        // Then inside weatherService.setOnSucceeded() service there are loops which iterate through this array, and save values to its cells.
        // Each array cell contains pointer to grid cell in application window.
        // This way values are saved to Grid Pane in Weather forecast section of window.
        // Without that array you would have to manually set values to every of these pointers (this.weatherForecastLabel10 etc)
        this.weatherForecastGridElements = new Node[][]{
                {null, this.weatherForecastLabel10, this.weatherForecastLabel20, this.weatherForecastLabel30, this.weatherForecastLabel40, this.weatherForecastLabel50},
                {this.weatherForecastLabel01, this.weatherForecastVbox11, this.weatherForecastVbox21, this.weatherForecastVbox31, this.weatherForecastVbox41, this.weatherForecastVbox51},
                {this.weatherForecastLabel02, this.weatherForecastVbox12, this.weatherForecastVbox22, this.weatherForecastVbox32, this.weatherForecastVbox42, this.weatherForecastVbox52}
        };

        // Pass values to 1. line of weather forecast grid - dates:
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
                    System.out.println(weatherService.getValue());
                    WeatherData weatherData = this.weatherService.getValue();

                    // Current weather section
                    if (weatherData.getLocation().getIsCurrentLocation()) {
                        this.currentLocationWeather = weatherData;

                        this.currentLocationName.setText(this.currentLocationWeather.getLocation().getName());
                        this.currentLocationLatitude.setText(this.currentLocationWeather.getLocation().getLatitude());
                        this.currentLocationLongitude.setText(this.currentLocationWeather.getLocation().getLongitude());

                        this.currentLocationIcon.setImage(new Image("/icons/" + this.currentLocationWeather.getCurrentWeather().getIconCode() + ".png"));
                        this.currentLocationDescriptionLable.setText(this.currentLocationWeather.getCurrentWeather().getDescription());
                        this.currentLocationTemperatureLabel.setText(this.currentLocationWeather.getCurrentWeather().getTemperature());
                        this.currentLocationFeelsLikeTemperatureLabel.setText(this.currentLocationWeather.getCurrentWeather().getFeelsLikeTemperature());
                        this.currentLocationWindSpeedLabel.setText(this.currentLocationWeather.getCurrentWeather().getWindSpeed());
                        this.currentLocationCloudinessLabel.setText(this.currentLocationWeather.getCurrentWeather().getCloudiness());
                        this.currentLocationHumidityLabel.setText(this.currentLocationWeather.getCurrentWeather().getHumidity());
                        this.currentLocationPressureLabel.setText(this.currentLocationWeather.getCurrentWeather().getPressure());

                        this.currentLocationWeatherVbox.setVisible(true);

                        this.errorLabel.setText("");
                    } else {
                        this.destinationWeather = weatherData;

                        this.destinationName.setText(this.destinationWeather.getLocation().getName());
                        this.destinationLatitude.setText(this.destinationWeather.getLocation().getLatitude());
                        this.destinationLongitude.setText(this.destinationWeather.getLocation().getLongitude());

                        this.destinationIcon.setImage(new Image("/icons/" + this.destinationWeather.getCurrentWeather().getIconCode() + ".png"));
                        this.destinationDescriptionLabel.setText(this.destinationWeather.getCurrentWeather().getDescription());
                        this.destinationTemperatureLabel.setText(this.destinationWeather.getCurrentWeather().getTemperature());
                        this.destinationFeelsLikeTemperatureLabel.setText(this.destinationWeather.getCurrentWeather().getFeelsLikeTemperature());
                        this.destinationWindSpeedLabel.setText(this.destinationWeather.getCurrentWeather().getWindSpeed());
                        this.destinationCloudinessLabel.setText(this.destinationWeather.getCurrentWeather().getCloudiness());
                        this.destinationHumidityLabel.setText(this.destinationWeather.getCurrentWeather().getHumidity());
                        this.destinationPressureLabel.setText(this.destinationWeather.getCurrentWeather().getPressure());

                        this.destinationWeatherVbox.setVisible(true);
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
                                ((Label) ((VBox) this.weatherForecastGridElements[1][i + 1]).getChildren().getLast()).setText(this.currentLocationWeather.getWeatherForecast().getWeatherForecastEntries().get(i).getFeelsLikeTemperature() + " °C");
                            }
                        }

                        for (int i = 0; i < 6; i++) {
                            this.weatherForecastGridElements[1][i].setVisible(true);
                            this.weatherForecastGridElements[1][i].setManaged(true);
                        }
                    } else {
                        ((Label) this.weatherForecastGridElements[2][0]).setText(this.destinationWeather.getLocation().getName());
                        for (int i = 0; i < this.destinationWeather.getWeatherForecast().getWeatherForecastEntries().size(); i++) {

                            // Set 3. line - destination weather forecast
                            if (this.weatherForecastDates[i].equals(this.destinationWeather.getWeatherForecast().getWeatherForecastEntries().get(i).getLocalDate())) {
                                ((ImageView) ((VBox) this.weatherForecastGridElements[2][i + 1]).getChildren().getFirst()).setImage((new Image("/icons/" + this.destinationWeather.getWeatherForecast().getWeatherForecastEntries().get(i).getIconCode() + ".png")));
                                ((Label) ((VBox) this.weatherForecastGridElements[2][i + 1]).getChildren().getLast()).setText(this.destinationWeather.getWeatherForecast().getWeatherForecastEntries().get(i).getFeelsLikeTemperature() + " °C");
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
     *
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
     *
     * @param textField (TextField) - text field used to fill city name in current location or destination section.
     * @return fale - if text field is blank or empty, true otherwise
     */
    private boolean validateField (TextField textField) {
        if (textField.getText().isBlank()) {
            return false;
        }
        return true;
    }

    /**
     * Method updates Location object before sending request if necessary.
     * <ul>
     *     <li>If city name typed in text field is different from city name saved in Location object, it means, a user typed new city.
     *      In this case method clears longitude and latitude in the location object,
     *      and getWeather services will use city name to get data (and not longitude and latitude).</li>
     *      <li> Otherwise it means, a user didn't type new city, and it is still the same location as in previous request.
     *      In this case method doesn't update location object,
     *      and getWeather services will use longitude and latitude to get data (and not city name).
     *      </li>
     * </ul>
     *
     * @param textField (TextField) - text field used to fill city name in current location or destination section.
     * @param location (Location) currentLocation or destinationLocation
     */
    private void updateLocationIfNecessary (TextField textField, Location location) {
        if (!textField.getText().equalsIgnoreCase(location.getName())) {
            location.setName(textField.getText());
            location.setLongitude("");
            location.setLatitude("");
        }
    }
}
