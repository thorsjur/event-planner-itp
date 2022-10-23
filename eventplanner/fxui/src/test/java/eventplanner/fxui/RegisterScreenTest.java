package eventplanner.fxui;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

class RegisterScreenTest extends ApplicationTest {

    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }

    @Override
    public void start(Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegisterScreen.fxml"));
        final Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    public void testNoInput() {
        clickOn("#createUserButton");
        String errorText = lookup("#errorOutput").queryAs(Label.class).getText();
        assertEquals("Date is not set. (1)", errorText, "Error output should update");
        
        DatePicker dp = lookup("#birthDatePicker").queryAs(DatePicker.class);
        dp.setValue(LocalDate.of(2001, 8, 4));
        clickOn("#createUserButton");
        String errorText2 = lookup("#errorOutput").queryAs(Label.class).getText();
        assertEquals("User already exists or invalid input. (2)", errorText2, "Error output should update");
    }

    @Test
    public void testRegisterUser() {
        String email = "test@test.org";
        String password = "" + System.currentTimeMillis();
        clickOn("#emailField").write(email);
        clickOn("#passwordField").write(password);
        DatePicker dp = lookup("#birthDatePicker").queryAs(DatePicker.class);
        dp.setValue(LocalDate.of(2001, 8, 4));
        clickOn("#createUserButton");

        // User got added
        User user1 = new User(email, password, true);
        Collection<User> users = FxuiTestUtil.loadUsers();
        assertNotNull(users.stream()
            .filter(e -> e.equals(user1))
            .findAny()
            .orElse(null));
    }

    @Test
    public void testGoToLoginPage() {
        // No such button on register page
        assertThrows(EmptyNodeQueryException.class, () -> FxAssert.verifyThat("#loginButton", LabeledMatchers.hasText("Login")));
        clickOn("#goToLoginButton");

        // Should now be on login page
        FxAssert.verifyThat("#loginButton", LabeledMatchers.hasText("Login"));
    }

    
}
