package eventplanner.fxui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

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

    private static boolean validateRegister(User user) {
        
        if(!Pattern.compile("^(.+)@(\\S+)$").matcher(user.email()).matches()) {
            return false;
        } else if(user.password() == null) {
            return false;
        };

        return true;
    }

    private boolean checkIfAbove18(LocalDate localDate) {
        return (Period.between(localDate, LocalDate.now()).getYears() > 17);
    }

    private Boolean createUser(User user) {
        boolean saveflag = false;
        if (validateRegister(user)) {
            try {
                IOUtil.appendUserToFile(user, null);
                saveflag = true;
            } catch (IOException e) {
                System.out.println("Something went wrong while saving\nCan't add user to file.");
                e.printStackTrace();
            }            
        } else {
            System.out.println("User not valid");
        }
        return saveflag;
    }

    @FXML
    private void handleCreateUser() {
        User newUser = new User(inputEmail.getText(), inputPassword.getText(), checkIfAbove18(inputBirthDate.getValue()));
        
        if (createUser(newUser)) {
            String fxmlFileName = "AllEvents.fxml";
            FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, AppController.class, newUser);
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
