package eventplanner.fxui;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.service.query.EmptyNodeQueryException;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.dataaccess.LocalDataAccess;
import eventplanner.fxui.util.ControllerUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

class RegisterScreenTest extends ApplicationTest {

    private static Collection<Event> eventsBackup = new ArrayList<>();
    private static Collection<User> usersBackup = new ArrayList<>();

    @BeforeAll
    static void setupHeadless() {
        App.supportHeadless();

        eventsBackup = FxuiTestUtil.getAllEvents();
        usersBackup = FxuiTestUtil.getAllUsers();
    }

    @AfterAll
    static void teardown() {
        FxuiTestUtil.restoreEvents(eventsBackup);
        FxuiTestUtil.restoreUsers(usersBackup);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = ControllerUtil.getFXMLLoaderWithFactory("RegisterScreen.fxml",
                RegisterController.class, null, new LocalDataAccess());

        final Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    void testNoInput() {
        clickOn("#createUserButton");
        String errorText = lookup("#errorCounter").queryAs(Label.class).getText();
        assertEquals("(1)", errorText, "Error output should update");

        DatePicker dp = lookup("#birthDatePicker").queryAs(DatePicker.class);
        dp.setValue(LocalDate.of(2001, 8, 4));
        clickOn("#createUserButton");
        String errorText2 = lookup("#errorCounter").queryAs(Label.class).getText();
        assertEquals("(2)", errorText2, "Error output should update");
    }

    @Test
    void testRegisterUserThenLogin() {
        String email = "test@user.co.uk";
        String password = "password";
        clickOn("#emailField").write(email);
        clickOn("#passwordField").write(password);
        DatePicker dp = lookup("#birthDatePicker").queryAs(DatePicker.class);
        dp.setValue(LocalDate.of(2001, 8, 4));
        clickOn("#createUserButton");

        // User can log out and back in, meaning they got registered
        clickOn("#logOutButton");
        clickOn("#emailField").write(email);
        clickOn("#passwordField").write(password);
        clickOn("#loginButton");

        // Should now be on allEvents page
        assertDoesNotThrow(() -> FxAssert.verifyThat("#logOutButton", LabeledMatchers.hasText("Log Out")));
    }

    @Test
    void testGoToLoginPage() {
        // No such button on register page
        assertThrows(EmptyNodeQueryException.class,
                () -> FxAssert.verifyThat("#loginButton", LabeledMatchers.hasText("Login")));
                
        clickOn("#goToLoginButton");

        // Should now be on login page
        assertDoesNotThrow(() -> FxAssert.verifyThat("#loginButton", LabeledMatchers.hasText("Login")));
    }

}
