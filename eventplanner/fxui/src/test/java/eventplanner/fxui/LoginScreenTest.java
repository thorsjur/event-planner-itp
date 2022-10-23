package eventplanner.fxui;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.service.query.EmptyNodeQueryException;

import eventplanner.core.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

class LoginScreenTest extends ApplicationTest {

    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }

    @Override
    public void start(Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        final Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    public void testLogin() {
        Collection<User> users = FxuiTestUtil.loadUsers();
        User user;
        if (users.stream().findFirst().isPresent()) {
            user = users.stream().findFirst().get();
        }
        else {
            System.out.println("No users registered");
            return;
        }
        String email = user.email();
        String password = user.password().substring(0, user.password().length()-1);

        clickOn("#emailField").write(email);
        clickOn("#passwordField").write(password + "ops");
        clickOn("#loginButton");

        // Wrong password
        String errorText = lookup("#errorOutput").queryAs(Label.class).getText();
        assertEquals("Wrong username or password. (1)", errorText, "Error output should update");
        
        // Correct password, should activate login
        doubleClickOn("#passwordField").write(password);
        clickOn("#loginButton");
        FxAssert.verifyThat("#logOutButton", LabeledMatchers.hasText("Log Out"));
    }

    @Test
    public void testGoToRegisterPage() {
        // No such button on login page
        assertThrows(EmptyNodeQueryException.class, () -> FxAssert.verifyThat("#goToLoginButton", LabeledMatchers.hasText("Go to login")));
        clickOn("#registerUserButton");
        
        // Should now be on register page
        FxAssert.verifyThat("#goToLoginButton", LabeledMatchers.hasText("Go to login"));
    }
}
