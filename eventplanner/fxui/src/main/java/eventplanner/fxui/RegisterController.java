package eventplanner.fxui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;

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
    private Button btnExit, btnCreateUser, btnLoginPage;

    @FXML
    private TextField inputEmail;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private DatePicker inputBirthDate;

    @FXML
    private Label errorOutput;

    @FXML
    public void initialize() {
    }

    private boolean validateRegister(String email, String password, LocalDate localDate) {
        //TODO
        return true;
    }

    private boolean checkIfAbove18(LocalDate localDate) {
        if (Period.between(localDate, LocalDate.now()).getYears() > 17 ) {
            return true;
        } else {
            return false;
        } 
    }

    private User createUser(String email, String password, LocalDate localDate) {
        return new User(email, password, checkIfAbove18(localDate)); //TODO - trengs det å legge til en serelisering her?
    }

    @FXML
    private void handleCreateUser() {
        if (validateRegister(inputEmail.getText(), inputPassword.getText(), inputBirthDate.getValue())) {
            User user = createUser(inputEmail.getText(), inputPassword.getText(), inputBirthDate.getValue());
            String fxmlFileName = "AllEvents.fxml";
            FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, AppController.class, user);
            ControllerUtil.setSceneFromChild(loader, btnCreateUser);
        } else {
            this.counter ++;
            errorOutput.setText("User already exists or invalid input; " + Integer.toString(this.counter));
        }
    }

    @FXML
    private void handleBtnLoginPage() {
        String fxmlFileName = "LoginScreen.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoader(fxmlFileName);
        ControllerUtil.setSceneFromChild(loader, btnLoginPage);
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

}
