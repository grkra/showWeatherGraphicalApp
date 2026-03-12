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

    public void showMainWindow() {
        BaseController controller = new MainWindowController("MainWindow");
        showWindow(controller);
    }

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
