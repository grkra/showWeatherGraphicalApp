package view;

import controller.BaseController;
import controller.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class creates, displays, and closes windows of the application.
 */
public class ViewFactory {

    /**
     * Method displays main window of the application.
     */
    public void showMainWindow() {
        BaseController controller = new MainWindowController("MainWindow");
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
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
