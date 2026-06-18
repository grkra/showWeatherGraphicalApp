package krawczyk.grzegorz.view;

import krawczyk.grzegorz.WeatherManager;
import krawczyk.grzegorz.controller.BaseController;
import krawczyk.grzegorz.controller.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Class creates, displays, and closes windows of the application.
 */
public class ViewFactory {

    private WeatherManager weatherManager;

    public ViewFactory(WeatherManager weatherManager) {
        this.weatherManager = weatherManager;
    }

    /**
     * Method displays main window of the application.
     */
    public void showMainWindow() {
        BaseController controller = new MainWindowController(weatherManager, "MainWindow");
        showWindow(controller);
    }

    /**
     * Method is used to initialize and display window of the application.
     * It is important to pass right controller, because it is used to find right fxml file.
     * @param controller (BaseController) controller connected with the window being displayed.
     */
    private void showWindow(BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_files/" + controller.getFxmlName() + ".fxml"));
        fxmlLoader.setController(controller);

        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("Failed to load FXML file: " + controller.getFxmlName());
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css_files/default.css")).toExternalForm());
        Stage stage = new Stage();
        stage.setTitle("Check your weather");
        stage.setScene(scene);
        stage.show();
    }
}
