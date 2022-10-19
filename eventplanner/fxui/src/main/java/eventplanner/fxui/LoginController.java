package eventplanner.fxui;

import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller for startup view.
 */
public class LoginController {

    private int counter = 0;

    @FXML
    private Button btnExit, btnLogin, btnRegisterPage;

    @FXML
    private TextField inputEmail;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private Label errorOutput;

    private User validateUser(String email, String password) {
        return findUser(email); //TODO - denne skal først finne bruker findUser(email) for så å sjekke om passordet stemmer.
    }

    private User findUser(String email) {
        return new User("TODO@TODO.TODO", "TODO", true); //TODO - denne skal finne en user
    }

    @FXML
    private void handleLogin() {
        User user = validateUser(inputEmail.getText(), inputPassword.getText());
        if (user != null) {
            String fxmlFileName = "AllEvents.fxml";
            FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, AppController.class, user);
            ControllerUtil.setSceneFromChild(loader, btnLogin);
        } else {
            this.counter ++;
            errorOutput.setText("Wrong username or password; " + Integer.toString(this.counter));
        }
    }

    @FXML
    private void handleBtnRegisterPage() {
        String fxmlFileName = "RegisterScreen.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoader(fxmlFileName);
        ControllerUtil.setSceneFromChild(loader, btnRegisterPage);
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

}
