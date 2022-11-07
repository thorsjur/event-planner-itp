package eventplanner.fxui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import eventplanner.core.Event;
import eventplanner.core.EventType;
import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

class CreateEventTest extends ApplicationTest {

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
    void testCreateValidEvent() {
        String email = "test@user.co.uk";
        String password = "password";
        registerUser(email, password);
        clickOn("#createEventButton");

        ComboBox<String> cb = lookup("#typeComboBox").queryComboBox();
        Platform.runLater(() -> cb.getSelectionModel().selectFirst());

        DatePicker dp = lookup("#startDatePicker").queryAs(DatePicker.class);
        dp.setValue(LocalDate.of(2022, 2, 5));
        clickOn("#startTimeField").write("13:00");

        DatePicker dp2 = lookup("#endDatePicker").queryAs(DatePicker.class);
        dp2.setValue(LocalDate.of(2023, 2, 5));

        clickOn("#endTimeField").write("13:00");
        clickOn("#nameField").write("TestName");
        clickOn("#locationField").write("TestLoc");
        clickOn("#descField").write("TestDesc");
        clickOn("#createButton");

        // Verify that the event is created
        Event expectedEvent = new Event(null, EventType.CONCERT, "TestName", LocalDateTime.of(2022, 2, 5, 13, 0),
                LocalDateTime.of(2023, 2, 5, 13, 0), "TestLoc", null, email, "TestDesc");

        clickOn("#eventsButton");
        ListView<Event> listView = lookup("#eventsListView").queryListView();
        Event addedEvent = listView.getItems().stream().filter(e -> FxuiTestUtil.areEventsEqual(expectedEvent, e))
                .findFirst().orElse(null);

        assertTrue(FxuiTestUtil.areEventsEqual(expectedEvent, addedEvent));
    }

    private void registerUser(String email, String password) {
        clickOn("#emailField").write(email);
        clickOn("#passwordField").write(password);
        DatePicker dp = lookup("#birthDatePicker").queryAs(DatePicker.class);
        dp.setValue(LocalDate.of(2001, 8, 4));
        clickOn("#createUserButton");
    }

}