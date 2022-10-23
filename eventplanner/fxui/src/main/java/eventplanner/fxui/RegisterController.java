package eventplanner.fxui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import eventplanner.json.util.IOUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller for register view.
 */
public class RegisterController {

    private int counter = 0;

    @FXML
    private Button exitButton, createUserButton, goToLoginButton;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private Label errorOutput;

    private static boolean isOlderThan18(LocalDate localDate) {
        return Period.between(localDate, LocalDate.now()).getYears() >= 18;
    }

    private static User createUser(String email, String password, boolean isAbove18) {
        User user;
        try {
            user = new User(email, password, isAbove18);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }

        try {
            IOUtil.appendUserToFile(user, null);
        } catch (IOException e) {
            System.out.println("Something went wrong while saving\nCan't add user to file.");
            return null;
        }

        return user;
    }

    @FXML
    private void handleCreateUser() {
        LocalDate date = birthDatePicker.getValue();
        if (date == null) {
            errorOutput.setText("Date is not set. (" + Integer.toString(++counter) + ")");
            return;
        }

        User user = createUser(emailField.getText(), passwordField.getText(), isOlderThan18(date));
        if (user != null) {
            String fxmlFileName = "AllEvents.fxml";
            FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, AllEventsController.class, user);
            ControllerUtil.setSceneFromChild(loader, createUserButton);
        } else {
            errorOutput.setText("User already exists or invalid input. (" + Integer.toString(++counter) + ")");
        }
    }

    @FXML
    private void handleGoToLoginPageButtonClicked() {
        String fxmlFileName = "LoginScreen.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoader(fxmlFileName);
        ControllerUtil.setSceneFromChild(loader, goToLoginButton);
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

}
