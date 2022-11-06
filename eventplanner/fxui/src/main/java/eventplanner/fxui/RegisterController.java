package eventplanner.fxui;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import eventplanner.fxui.util.InputType;
import eventplanner.fxui.util.Validation;
import eventplanner.fxui.util.Validation.ErrorType;
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
    private Label errorOutput, errorCounter;

    private DataAccess dataAccess;

    public RegisterController(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @FXML
    private void initialize() {

        // Adds listeners to all input fields that signals input-validity to the user.
        ControllerUtil.addValidationFocusListener(emailField, InputType.EMAIL);
        ControllerUtil.addValidationFocusListener(passwordField, InputType.PASSWORD);
        ControllerUtil.addValidationFocusListener(birthDatePicker, InputType.BIRTH_DATE);
    }

    @FXML
    private void handleCreateUser() {
        List<ErrorType> errors = validateUserInputs();
        if (errors.size() > 0) {
            displayErrorMessages(errors);
            incrementErrorCounter();
            return;
        }

        User user = createUser(emailField.getText(), passwordField.getText(),
                isOlderThan18(birthDatePicker.getValue()));
        if (user != null) {
            String fxmlFileName = "AllEvents.fxml";
            FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, AllEventsController.class, user,
                    this.dataAccess);
            ControllerUtil.setSceneFromChild(loader, createUserButton);
        } else {
            errorOutput.setText("User already exists!");
            incrementErrorCounter();
        }
    }

    private User createUser(String email, String password, boolean isAbove18) {
        User user;
        if (dataAccess.getUser(email) == null) {
            user = new User(email, password, isAbove18);
            boolean wasCreated = dataAccess.createUser(user);

            if (!wasCreated) {
                errorOutput.setText(ControllerUtil.SERVER_ERROR);
                return null;
            }
        } else {
            user = null;
        }

        return user;
    }

    @FXML
    private void handleGoToLoginPageButtonClicked() {
        String fxmlFileName = "LoginScreen.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, LoginController.class, null,
                this.dataAccess);
        ControllerUtil.setSceneFromChild(loader, goToLoginButton);
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    private List<ErrorType> validateUserInputs() {
        List<ErrorType> errors = new ArrayList<>();

        if (!Validation.isValidTextInput(emailField.getText(), InputType.EMAIL)) {
            errors.add(Validation.ErrorType.INVALID_EMAIL);
        }

        if (!Validation.isValidTextInput(passwordField.getText(), InputType.PASSWORD)) {
            errors.add(Validation.ErrorType.INVALID_PASSWORD);
        }

        if (!Validation.isValidDateInput(birthDatePicker.getValue(), InputType.BIRTH_DATE)) {
            errors.add(Validation.ErrorType.INVALID_BIRTH_DATE);
        }

        return errors;
    }

    private void incrementErrorCounter() {
        errorCounter.setText("(" + ++counter + ")");
    }

    private void displayErrorMessages(List<ErrorType> errors) {
        StringBuilder sb = new StringBuilder();
        errors.forEach(error -> sb.append(error.errMessage + "\n"));
        errorOutput.setText(sb.toString());
    }

    private static boolean isOlderThan18(LocalDate localDate) {
        return Period.between(localDate, LocalDate.now()).getYears() >= 18;
    }

}
