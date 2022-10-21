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
    private Button btnExit, btnLogin, btnRegisterPage;

    @FXML
    private TextField inputEmail;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private Label errorOutput;

    @FXML
    private void handleLogin() {
        if (isValidLogin(inputEmail.getText(), inputPassword.getText())) {
            User user;
            try {
                user = IOUtil.loadUserMatchingEmail(inputEmail.getText(), null);
            } catch (IOException e) {
                System.out.println("Something went wrong loading user from file");
                return;
            }

            String fxmlFileName = "AllEvents.fxml";
            FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, AppController.class, user);
            ControllerUtil.setSceneFromChild(loader, btnLogin);
        } else {
            errorOutput.setText("Wrong username or password. (" + Integer.toString(++counter) + ")");
        }
    }

    private Boolean isValidLogin(String email, String password) {
        User user;
        try {
            user = IOUtil.loadUserMatchingEmail(email, null);
        } catch (IOException e) {
            System.out.println("Could not load users");
            return false;
        }
        if (user == null) {
            System.out.println("User not found");
            return false;
        }

        if (user.password().equals(UserUtil.passwordHash(password))) {
            System.out.println("Found user, correct password.");
           return true;
        } else {
            System.out.println("Found user, wrong password.");
        }

        return false;
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
