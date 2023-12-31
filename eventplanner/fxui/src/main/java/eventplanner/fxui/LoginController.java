package eventplanner.fxui;

import eventplanner.core.User;
import eventplanner.dataaccess.DataAccess;
import eventplanner.dataaccess.RemoteDataAccess;
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
    private Button exitButton, loginButton, registerUserButton;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorOutput;

    private DataAccess dataAccess;

    public LoginController(DataAccess dataAccess) {
        this.dataAccess = dataAccess.copy();
    }

    @FXML
    private void handleLogin() {
        User user = findUser(emailField.getText(), passwordField.getText());
        if (user != null) {
            String fxmlFileName = "AllEvents.fxml";
            FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, AllEventsController.class, user,
                    dataAccess);
            ControllerUtil.setSceneFromChild(loader, loginButton);
        } else if (dataAccess.isRemote()) {
            try {
                RemoteDataAccess.assertConnection();
                errorOutput.setText("Wrong username or password. (" + Integer.toString(++counter) + ")");
            } catch (Exception e) {
                errorOutput.setText(ControllerUtil.SERVER_ERROR);
            }
        } else {
            errorOutput.setText("Wrong username or password. (" + Integer.toString(++counter) + ")");
        }
    }

    private User findUser(String email, String password) {
        User user = dataAccess.getUser(email);

        if (user == null) {
            System.out.println("User not found");
            errorOutput.setText(ControllerUtil.SERVER_ERROR);
            return null;
        }

        if (user.password().equals(password)) {
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
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, RegisterController.class, null,
                dataAccess);
        ControllerUtil.setSceneFromChild(loader, registerUserButton);
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

}
