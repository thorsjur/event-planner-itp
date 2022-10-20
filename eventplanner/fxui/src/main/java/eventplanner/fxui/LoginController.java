package eventplanner.fxui;

import eventplanner.core.User;
import eventplanner.core.UserUtil;
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

    private Boolean validateUser(String email, String password) {

        try {
            User checkUser = UserUtil.findUser(email);
            if (checkUser.password().equals(UserUtil.passwordHash(password))) {
                System.out.println("Found user, correct password.");
               return true;
            } else {
                System.out.println("Found user, wrong password.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Could not find user.");
            return false;
        }
    }

    @FXML
    private void handleLogin() {

        if (inputPassword.getText().isBlank() || inputEmail.getText().isBlank()) {
            System.out.println("Input password or email is null.");
            counter++;
            errorOutput.setText("Wrong username or password; "+ Integer.toString(counter));
        } else if (validateUser(inputEmail.getText(), inputPassword.getText())) {
            String fxmlFileName = "AllEvents.fxml";
            FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, AppController.class, UserUtil.findUser(inputEmail.getText()));
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
