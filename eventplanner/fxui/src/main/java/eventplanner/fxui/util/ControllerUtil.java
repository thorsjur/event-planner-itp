package eventplanner.fxui.util;

import java.io.IOException;
import java.util.function.Supplier;

import eventplanner.fxui.App;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class ControllerUtil {

    public static void setSceneFromChild(String resourcePath, Node child) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(resourcePath));
        try {
            fxmlLoader.load();
            child.getScene().setRoot(fxmlLoader.getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
