package krawczyk.grzegorz;

import javafx.application.Application;
import javafx.stage.Stage;
import krawczyk.grzegorz.view.ViewFactory;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        ViewFactory viewFactory = new ViewFactory();
        viewFactory.showMainWindow();
    }
}
