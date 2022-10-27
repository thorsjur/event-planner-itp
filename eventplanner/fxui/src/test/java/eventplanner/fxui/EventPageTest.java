package eventplanner.fxui;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.util.function.Predicate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import eventplanner.core.Event;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class EventPageTest extends ApplicationTest  {

    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }

    @AfterAll
    public static void name() {
        FxuiTestUtil.cleanUpEvents();
        FxuiTestUtil.cleanUpUsers();
    }

    @Override
    public void start(Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegisterScreen.fxml"));
        final Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    public void testEventPageRegistrationDeregistrationAndAuthorDeletion() {
        String email = "test@test.org";
        String password = "password";
        registerUser(email, password);
        createEvent();

        clickOn(findEventCellNode(cell -> true, ".button"));

        assertDoesNotThrow(() -> FxAssert.verifyThat("#returnButton", LabeledMatchers.hasText("Return")), "Should be on eventPage");
        
        // Checking that the clicked on event is the one that was just created
        if (lookup("#authorLabel").queryLabeled().getText().equals("test@test.org")) {
            assertDoesNotThrow(() -> FxAssert.verifyThat("#deleteEventButton", LabeledMatchers.hasText("DELETE EVENT")), "Should be able to delete event since user is author for \"TestName\"");
        }
        else {
            fail("Event TestName should have been the one selected");
        }

        // Checking that registration and deregistration works
        clickOn("#registerButton");
        assertDoesNotThrow(() -> FxAssert.verifyThat("#regUsersLabel", LabeledMatchers.hasText("1")), "Users registered should update to 1");
        clickOn("#registerButton");
        assertDoesNotThrow(() -> FxAssert.verifyThat("#regUsersLabel", LabeledMatchers.hasText("0")), "User registered should update to 0 after deregistration");

        // Checking that author can delete event
        clickOn("#deleteEventButton");
        Node dialogPane = lookup(".dialog-pane").query();
        clickOn(from(dialogPane).lookup(".button").match(LabeledMatchers.hasText("Delete Event")).queryButton());
        
        ListView<Event> listView = lookup("#allEventsList").queryListView();
        clickOn("#searchBar").write("TestName");
        listView.getSelectionModel().selectAll();
        assertFalse(listView.getSelectionModel().getSelectedItems().stream()
                .anyMatch(e -> e.getAuthorEmail().equals("test@test.org")), "Event should be deleted and not in listView of events");
    }

    
    private Node findEventCellNode(Predicate<EventCell> test, String selector) {
        Node listCell = lookup(".list-cell").query();
        return listCell.lookup(selector);
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
        dp.setValue(LocalDate.of(2000, 1, 2));
        clickOn("#startTimeField").write("00:00");

        DatePicker dp2 = lookup("#endDatePicker").queryAs(DatePicker.class);
        dp2.setValue(LocalDate.of(2000, 1, 2));

        clickOn("#endTimeField").write("00:01");
        clickOn("#nameField").write("TestName");
        clickOn("#locationField").write("TestLoc");
        clickOn("#descField").write("TestDesc");
        clickOn("#createButton");
        clickOn("#eventsButton");
    }
    
}