package eventplanner.fxui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.dataaccess.LocalDataAccess;
import eventplanner.fxui.util.ControllerUtil;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

class AllEventsTest extends ApplicationTest {

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
    void testSearchAndRegisterDeregisterEvent() {
        String email = "test@email.co.uk";
        String password = "password";
        registerUser(email, password);
        createEvent();

        ListView<Event> listView = lookup("#eventsListView").queryListView();
        Event event = listView.getItems().get(0);

        // Can't find event by search before saving event
        clickOn("#myEventsCheckBox");
        clickOn("#searchBar").write(event.getName());
        listView.getSelectionModel().selectAll();
        assertFalse(listView.getSelectionModel().getSelectedItems().stream().anyMatch(e -> e.equals(event)));

        clickOn("#myEventsCheckBox");

        listView.getSelectionModel().select(event);
        clickOn("#saveEventButton");
        clickOn("#myEventsCheckBox");

        // Can find event by search (serach word is still in the searchbar)
        listView.getSelectionModel().selectAll();
        assertTrue(listView.getSelectionModel().getSelectedItems().stream().anyMatch(e -> e.equals(event)));

        listView.getSelectionModel().select(event);
        clickOn("#saveEventButton");

        // Event deregistered
        listView.getSelectionModel().selectAll();
        assertFalse(listView.getSelectionModel().getSelectedItems().stream().anyMatch(e -> e.equals(event)));
    }

    private void registerUser(String email, String password) {
        clickOn("#emailField").write(email);
        clickOn("#passwordField").write(password);
        DatePicker dp = lookup("#birthDatePicker").queryAs(DatePicker.class);
        dp.setValue(LocalDate.of(2001, 8, 4));
        clickOn("#createUserButton");

    }

    private void createEvent() {
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
        clickOn("#eventsButton");
    }

}