package eventplanner.fxui;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import eventplanner.json.EventCollectionJsonReader;
import eventplanner.json.EventCollectionJsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

/**
 * Controller for "myEvents" view.
 */
public class MyEventsController {

    @FXML
    private Button createEventButton, eventsButton;

    @FXML
    private ListView<Event> myEventsList;

    @FXML
    private Label removeEventLabel;

    private User user;
    private Collection<Event> eventCollection;

    public MyEventsController(User user) {
        this.user = user;
    }

    @FXML
    private void initialize() {
        updateSavedEventsListView();
    }

    /**
     * Removes user from selected items' list of users,
     * writes changes to file and removes the selected
     * events from view.
     */
    @FXML
    private void handleRemoveEventButtonClicked() {
        if (myEventsList.getSelectionModel().getSelectedItem() == null) {
            removeEventLabel.setText("No events chosen");
        } else {
            Collection<Event> selectedEvents = myEventsList.getSelectionModel().getSelectedItems();
            selectedEvents.forEach(event -> event.removeUser(this.user));

            EventCollectionJsonWriter writer = new EventCollectionJsonWriter();
            try {
                writer.save(eventCollection);
            } catch (IOException e) {
                System.out.println("Error occurred while saving events to file.");
                return;
            }
            myEventsList.getItems().removeAll(selectedEvents);
            removeEventLabel.setText("Events removed \n from 'My events'");
        }
    }

    /**
     * Reads/loads events from default file and displays the
     * events that contain the user in its users list.
     */
    private void updateSavedEventsListView() {
        EventCollectionJsonReader reader = new EventCollectionJsonReader();
        try {
            eventCollection = reader.load();
        } catch (IOException e) {
            eventCollection = new ArrayList<>();
            System.out.println("Error occurred loading events ...");
        }

        List<Event> savedEvents = getUsersSavedEvents();

        Collections.sort(savedEvents, ControllerUtil.getReverseDateComparator());

        myEventsList.getItems().addAll(savedEvents);

        // Makes it possible to choose multiple items in list view
        // by holding ctrl+cmd while selecting items
        myEventsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        myEventsList.setCellFactory(param -> new EventCell());
    }

    private List<Event> getUsersSavedEvents() {
        return eventCollection.stream()
                .filter(event -> {
                    return event.getUsers().stream()
                            .anyMatch(user -> user.email().equals(this.user.email())); //TODO - pass på følgefeil
                })
                .collect(Collectors.toList());
    }

    @FXML
    private void handleEventsButtonClicked() {
        String fxmlFileName = "AllEvents.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, AppController.class, user);
        ControllerUtil.setSceneFromChild(loader, eventsButton);
    }

    @FXML
    private void handleCreateEventButtonClicked() {
        String fxmlFileName = "CreateEvent.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, NewEventController.class, user);
        ControllerUtil.setSceneFromChild(loader, eventsButton);
    }
}
