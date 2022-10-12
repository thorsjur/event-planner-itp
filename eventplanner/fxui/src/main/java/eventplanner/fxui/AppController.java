package eventplanner.fxui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import eventplanner.json.EventCollectionJsonReader;
import eventplanner.json.EventCollectionJsonWriter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

/**
 * TODO Javadoc.
 */
public class AppController {

    @FXML
    private Button createEventButton, myEventsButton;

    @FXML
    private Label saveEventLabel;

    @FXML
    private ListView<Event> allEventsList;

    private User user;

    public AppController(User user) {
        this.user = user;
    }

    /**
     * Reads events from file and displays events in
     * view, sorted after date and time.
     */
    @FXML
    private void initialize() {
        Collection<Event> eventCollection;

        try {
            EventCollectionJsonReader reader = new EventCollectionJsonReader();
            eventCollection = reader.load();
        } catch (IOException e) {
            eventCollection = new ArrayList<>();
            System.out.println("Could not load events");
        }

        ArrayList<Event> sortedEvents = new ArrayList<>(eventCollection);
        Collections.sort(sortedEvents, (e1, e2) -> e2.getStartDate().compareTo(e1.getStartDate()));
        allEventsList.getItems().addAll(sortedEvents);

        // Makes it possible to choose multiple items in list view
        // by holding ctrl+cmd while selecting items
        allEventsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Sets the CellFactory for the listview to produce EventCells (custom cells)
        allEventsList.setCellFactory((param) -> new EventCell());
    }

    /**
     * Adds user to selected events' lists of
     * users and writes canges to file.
     */
    @FXML
    private void handleSaveEventButtonClicked() {
        if (allEventsList.getSelectionModel().getSelectedItem() != null) {
            ObservableList<Event> selectedEvents = allEventsList
                    .getSelectionModel()
                    .getSelectedItems();

            selectedEvents.forEach(event -> event.addUser(user));

            EventCollectionJsonWriter writer = new EventCollectionJsonWriter();
            try {
                writer.save(allEventsList.getItems());
                saveEventLabel.setText("Events saved \n to 'My events'");
            } catch (IOException e) {
                System.out.println("Could not save events ...");
                return;
            }
        } else {
            saveEventLabel.setText("No events chosen");
        }
    }

    @FXML
    private void handleMyEventsButtonClicked() {
        String fxmlFileName = "MyEvents.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, MyEventsController.class, user);
        ControllerUtil.setSceneFromChild(loader, myEventsButton);
    }

    @FXML
    private void handleCreateEventButtonClicked() {
        String fxmlFileName = "CreateEvent.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, NewEventController.class, user);
        ControllerUtil.setSceneFromChild(loader, myEventsButton);
    }
}
