package eventplanner.fxui;

import java.io.IOException;

import eventplanner.core.User;
import eventplanner.core.util.UserUtil;
import eventplanner.fxui.util.ControllerUtil;
import eventplanner.json.util.IOUtil;
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
    private Button exitButton, loginButton, registerUserButton;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorOutput;

    @FXML
    private void handleLogin() {
        User user = findUser(emailField.getText(), passwordField.getText());
        if (user != null) {
            String fxmlFileName = "AllEvents.fxml";
            FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, AllEventsController.class, user);
            ControllerUtil.setSceneFromChild(loader, loginButton);
        } else {
            errorOutput.setText("Wrong username or password. (" + Integer.toString(++counter) + ")");
        }
    }

    private User findUser(String email, String password) {
        User user = DataAccess.getUser(email);

        if (user == null) {
            System.out.println("User not found");
            return null;
        }

        if (user.password().equals(UserUtil.passwordHash(password))) {
            System.out.println("Found user, correct password.");
            return user;
        } else {
            System.out.println("Found user, wrong password.");
        }
        return null;
    }

    @FXML
    private void handleRegisterUserButtonClicked() {
        String fxmlFileName = "RegisterScreen.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoader(fxmlFileName);
        ControllerUtil.setSceneFromChild(loader, registerUserButton);
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

}
