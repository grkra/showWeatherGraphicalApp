package controller;

/**
 * Base class to be extended in all controllers.
 */
public abstract class BaseController {

    private String fxmlFile;

    /**
     * Constructor of the BaseController class.
     * @param fxmlFile (String) - name of the fxml file.
     */
    public BaseController(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }

    /**
     * Method returns name of the fxml file connected with the controller.
     * @return (String) name of the fxml file.
     */
    public String getFxmlName() {
        return fxmlFile;
    };
}
