package eventplanner.fxui;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import static org.junit.Assert.assertTrue;
import static org.testfx.assertions.api.Assertions.assertThat;

class EventSignupTest extends ApplicationTest {

    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }

    @Override
    public void start(Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = ControllerUtil.getFXMLLoaderWithFactory("AllEvents.fxml", AppController.class,
                new User("test@test.test", "password", true)); //TODO - test om dette ble korrekt
        final Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    // @BeforeEach TODO
    // public void setup() {
    //     clickOn("#myEventsButton");
    //     clear();
    // }

    // @Test TODO
    // public void testCanSignUpToEvents() {
    //     ListView<Event> eventListView = lookup("#allEventsList").queryListView();
    //     clickOn(eventListView);
    //     Event clickedEvent = eventListView.getSelectionModel().getSelectedItem();
    //     clickOn("#saveEventButton");

    //     // Asserts that the event is saved to the user
    //     clickOn("#myEventsButton");
    //     ListView<Event> savedEventsListView = lookup("#myEventsList").queryListView();
    //     assertThat(savedEventsListView).hasExactlyNumItems(1);
    //     assertTrue(FxuiTestUtil.areEventsEqual(savedEventsListView.getItems().get(0), clickedEvent));

    //     // Selects two more items and asserts that there are three events.
    //     clickOn("#eventsButton");
    //     eventListView = lookup("#allEventsList").queryListView();
    //     eventListView.getSelectionModel().selectRange(0, 2);

    //     clickOn("#saveEventButton").clickOn("#myEventsButton");
    //     savedEventsListView = lookup("#myEventsList").queryListView();
    //     assertThat(savedEventsListView).hasExactlyNumItems(3);

    //     // switches pane and asserts that there are still three events
    //     clickOn("#createEventButton").clickOn("#myEventsButton");
    //     assertThat(savedEventsListView).hasExactlyNumItems(3);

    //     // Clears the events and asserts that there are no events saved to user
    //     clear();
    //     clickOn("#myEventsButton");
    //     savedEventsListView = lookup("#myEventsList").queryListView();
    //     assertThat(savedEventsListView).hasExactlyNumItems(0);
    // }

    // private void clear() {
    //     ListView<Event> listView = lookup("#myEventsList").queryListView();
    //     listView.getSelectionModel().selectAll();
    //     clickOn("#removeEventButton").clickOn("#eventsButton");
    // }

}