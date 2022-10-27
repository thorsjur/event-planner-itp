package eventplanner.fxui;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.service.query.EmptyNodeQueryException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

class LoginScreenTest extends ApplicationTest {

    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }

    @AfterAll
    public static void tearDown() {
        FxuiTestUtil.cleanUpUsers();
    }

    @Override
    public void start(Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        final Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    public void testLoginWithUnregisteredUser() {
        // No input
        clickOn("#loginButton");
        String errorText1 = lookup("#errorOutput").queryAs(Label.class).getText();
        assertEquals("Wrong username or password. (1)", errorText1, "Error output should update");

        String email = "test" + System.currentTimeMillis() + "@test.no";
        String password = "password";

        clickOn("#emailField").write(email);
        clickOn("#passwordField").write(password);
        clickOn("#loginButton");

        // No such user
        String errorText2 = lookup("#errorOutput").queryAs(Label.class).getText();
        assertEquals("Wrong username or password. (2)", errorText2, "Error output should update");
    }

    @Test
    public void testLoginWrongPassword() {
        // Register new user
        String email = "test@test.org";
        String password = "password";
        registerUser(email, password);
        clickOn("#logOutButton");

        // Log in to user with non equal password
        clickOn("#emailField").write(email);
        clickOn("#passwordField").write(password + "opsie");
        clickOn("#loginButton");

        // Wrong password
        String errorText1 = lookup("#errorOutput").queryAs(Label.class).getText();
        assertEquals("Wrong username or password. (1)", errorText1, "Error output should update");
        
        // Correct password, should activate login
        doubleClickOn("#passwordField").write(password);
        clickOn("#loginButton");
        assertDoesNotThrow(() -> FxAssert.verifyThat("#logOutButton", LabeledMatchers.hasText("Log Out")));
    }

    @Test
    public void testGoToRegisterPage() {
        // No such button on login page
        assertThrows(EmptyNodeQueryException.class, () -> FxAssert.verifyThat("#goToLoginButton", LabeledMatchers.hasText("Go to login")));
        clickOn("#registerUserButton");
        
        // Should now be on register page
        assertDoesNotThrow(() -> FxAssert.verifyThat("#goToLoginButton", LabeledMatchers.hasText("Go to login")));
    }

    private void registerUser(String email, String password) {
        clickOn("#registerUserButton");
        clickOn("#emailField").write(email);
        clickOn("#passwordField").write(password);
        DatePicker dp = lookup("#birthDatePicker").queryAs(DatePicker.class);
        dp.setValue(LocalDate.of(2001, 8, 4));
        clickOn("#createUserButton");
    }

}
