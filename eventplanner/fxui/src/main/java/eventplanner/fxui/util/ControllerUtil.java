package eventplanner.fxui.util;

import java.io.IOException;
import java.util.function.Supplier;

import eventplanner.fxui.App;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * Static utility methods to simplify logic in the controllers.
 */
public class ControllerUtil {
    
    /**
     * Takes a .fxml resourcePath and a child of the current scene
     * and sets a new root based on the resource path
     * 
     * @param resourcePath  name of the .fxml file
     * @param child         a child of the current scene
     */
    public static void setSceneFromChild(String resourcePath, Node child) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(resourcePath));
        try {
            fxmlLoader.load();
            child.getScene().setRoot(fxmlLoader.getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a ChangeListener based on a specific validation.
     * Runs actionIfValid if supplier validation return true,
     * otherwise it runs actionIfInvalid.
     * 
     * @param   validation          supplier that returns a boolean value indicating validity
     * @param   actionIfValid       action on valid supplier return
     * @param   actionIfInvalid     action of invalid supplier return
     * @return                      ChangeListener<Boolean> for change in object focus
     */
    public static ChangeListener<Boolean> getValidationFocusListener(Supplier<Boolean> validation, Runnable actionIfValid, Runnable actionIfInvalid) {
        return (arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (validation.get()) {
                    actionIfValid.run();
                } else {
                    actionIfInvalid.run();
                }
            }
        };
    }
}
