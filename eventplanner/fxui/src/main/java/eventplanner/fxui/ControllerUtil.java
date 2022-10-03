package eventplanner.fxui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class ControllerUtil {

    public static void setSceneFromChild(String resourcePath, Node child) {
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerUtil.class.getResource(resourcePath));
        try {
            fxmlLoader.load();
            child.getScene().setRoot(fxmlLoader.getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
