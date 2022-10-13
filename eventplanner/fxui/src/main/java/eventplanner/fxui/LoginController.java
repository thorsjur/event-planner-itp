package eventplanner.fxui;

import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller for startup view.
 */
public class LoginController {

    @FXML
    private Button btnCancel, btnConfirm;

    @FXML
    private TextField inputUsername;

    @FXML
    public void initialize() {
        inputUsername.setText("user" + ControllerUtil.getRandomFiveDigitString());
    }

    @FXML
    private void handleConfirm() {
        User user = new User(inputUsername.getText());
        String fxmlFileName = "AllEvents.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, AppController.class, user);
        ControllerUtil.setSceneFromChild(loader, btnConfirm);
    }

    @FXML
    private void handleCancel() {
        Platform.exit();
    }

}
