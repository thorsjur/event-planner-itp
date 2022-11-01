package eventplanner.fxui;

import java.time.LocalDate;
import java.time.Period;

import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import eventplanner.core.util.Validation;
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

    private DataAccess dataAccess;

    public RegisterController(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    private User createUser(String email, String password, boolean isAbove18) {
        User user;
        if (dataAccess.getUser(email) == null) {
            try {
                user = new User(email, password, isAbove18);
                dataAccess.createUser(user);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                errorOutput.setText(ControllerUtil.SERVER_ERROR);
                return null;
            }
        } else {
            user = null;
        }

        return user;
    }

    @FXML
    private void handleCreateUser() {
        try {
            if ((emailField.getText().isEmpty() 
                    || passwordField.getText().isBlank()
                    || birthDatePicker.getValue() == null
                    || !(validateInput(emailField.getText(), passwordField.getText(), birthDatePicker.getValue())))) {
                errorOutput.setText("Invalid input; " + ++counter);
                return;
            }
        } catch (Exception e) {
            errorOutput.setText("Invalid input or servererror; " + ++counter);
        }

        User user = createUser(emailField.getText(), passwordField.getText(), isOlderThan18(birthDatePicker.getValue()));
        if (user != null) {
            String fxmlFileName = "AllEvents.fxml";
            FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, AllEventsController.class, user, this.dataAccess);
            ControllerUtil.setSceneFromChild(loader, createUserButton);
        } else {
            errorOutput.setText("User already exists or invalid input. (" + Integer.toString(++counter) + ")");
        }
    }

    @FXML
    private void handleGoToLoginPageButtonClicked() {
        String fxmlFileName = "LoginScreen.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, LoginController.class, null, this.dataAccess);
        ControllerUtil.setSceneFromChild(loader, goToLoginButton);
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    private static boolean validateInput(String email, String password, LocalDate date) {
        return (Validation.isValidEmail(email) && Validation.isValidPassword(password) && Validation.isValidDate(date));
    }

    private static boolean isOlderThan18(LocalDate localDate) {
        return Period.between(localDate, LocalDate.now()).getYears() >= 18;
    }

}
