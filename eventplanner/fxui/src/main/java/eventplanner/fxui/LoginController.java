package eventplanner.fxui;

import eventplanner.fxui.util.ControllerUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {
    
    @FXML
    private Button createEventButton, eventsButton, myEventsButton;

    private ControllerUtil utils = new ControllerUtil();
    
    @FXML
    private Button btnCancel, btnConfirm;

    @FXML
    private TextField inputUsername;

    static String username;

    @FXML
    public void initialize() {
        inputUsername.setText("user" + utils.randNumGenerator());
    }

    @FXML
    private void handleConfirm() {
        username = inputUsername.getText();
        ControllerUtil.setSceneFromChild( "AllEvents.fxml", btnConfirm);    
    }

    @FXML
    private void handleCancel() {
        Platform.exit();
    }
    
}
