package krawczyk.grzegorz;

import javafx.application.Application;
import javafx.stage.Stage;
import krawczyk.grzegorz.view.ViewFactory;

public class Launcher extends Application {

    private WeatherManager weatherManager = new WeatherManager();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        ViewFactory viewFactory = new ViewFactory(weatherManager);
        viewFactory.showMainWindow();
    }
}
