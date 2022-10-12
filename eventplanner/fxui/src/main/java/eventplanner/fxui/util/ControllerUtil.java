package eventplanner.fxui.util;

import java.io.IOException;
import java.util.function.Supplier;
import java.util.Random;

import eventplanner.fxui.App;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * Static utility methods to simplify logic in the controllers.
 */
public class ControllerUtil {

    private static Random r = new Random();
    
    /**
     * Takes a .fxml resourcePath and a child of the current scene
     * and sets a new root based on the resource path
     * 
     * @param loader        name of the .fxml file
     * @param child         a child of the current scene
     */
    public static void setSceneFromChild(FXMLLoader loader, Node child) {
        try {
            loader.load();
            child.getScene().setRoot(loader.getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FXMLLoader getFXMLLoader(String filename) {
        return new FXMLLoader(App.class.getResource(filename));
    }


    /**
     * Returns a ChangeListener based on a specific validation.
     * Runs actionIfValid if supplier validation return true,
     * otherwise it runs actionIfInvalid.
     * 
     * @param   validation          supplier that returns a boolean value indicating validity
     * @param   actionIfValid       action on valid supplier return
     * @param   actionIfInvalid     action of invalid supplier return
     * @return                      ChangeListener for change in object focus
     */
    public static ChangeListener<Boolean> getValidationFocusListener(
                Supplier<Boolean> validation, 
                Runnable actionIfValid, 
                Runnable actionIfInvalid) {
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

    /**
     * Generates a 5-digit number with random integers between 1-10 in each digit.
     * 
     * @return 5-digit number
     */
    public String randNumGenerator() {
        int[] fiveRandomNumbers = r.ints(5, 0, 11).toArray();
        StringBuilder numbersAsString = new StringBuilder();
        for (int num : fiveRandomNumbers) {
            numbersAsString.append(num);
        }
        return numbersAsString.toString();
    }
}
