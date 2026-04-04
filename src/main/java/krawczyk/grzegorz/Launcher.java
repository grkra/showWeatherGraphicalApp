package krawczyk.grzegorz;

import javafx.application.Application;
import javafx.stage.Stage;
import krawczyk.grzegorz.controller.persistence.PersistenceAccess;
import krawczyk.grzegorz.view.ViewFactory;

public class Launcher extends Application {

    private WeatherManager weatherManager;
    private PersistenceAccess persistenceAccess = new PersistenceAccess();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.weatherManager = persistenceAccess.loadFromFile();

        System.out.println("--------------------------------------------------");
        System.out.println("WCZYTYWANIE:");
        System.out.println(this.weatherManager);
        ViewFactory viewFactory = new ViewFactory(weatherManager);
        viewFactory.showMainWindow();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("---------------------------------------------------");
        System.out.println("ZAPISYWANIE");
        System.out.println(this.weatherManager);
        this.persistenceAccess.saveToFile(this.weatherManager);
    }
}
