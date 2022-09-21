package project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class ControllerUtil {

    public static void setSceneFromChild(String resourcePath, Node child) {
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerUtil.class.getResource(resourcePath));
        child.getScene().setRoot(fxmlLoader.getRoot());
    }
}
