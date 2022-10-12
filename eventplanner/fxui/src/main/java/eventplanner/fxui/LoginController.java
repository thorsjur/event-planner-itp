package eventplanner.fxui;

import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * TODO Javadoc.
 */
public class LoginController {
    
    private ControllerUtil utils = new ControllerUtil();
    
    @FXML
    private Button btnCancel;
    
    @FXML
    private Button btnConfirm;

    @FXML
    private TextField inputUsername;


    @FXML
    public void initialize() {
        inputUsername.setText("user" + utils.randNumGenerator());
    }

    @FXML
    private void handleConfirm() {
        User user = new User(inputUsername.getText());
        String pathName = "AllEvents.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoader(pathName);
        ControllerUtil.setSceneFromChild(loader, btnConfirm);
        AppController appController = loader.getController();
        appController.setUser(user);   
    }

    @FXML
    private void handleCancel() {
        Platform.exit();
    }
    
}
