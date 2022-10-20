package eventplanner.fxui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import eventplanner.json.EventCollectionJsonReader;
import eventplanner.json.EventCollectionJsonWriter;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

/**
 * Controller for main view.
 */
public class AppController {

    @FXML
    private Button createEventButton;

    @FXML
    private Label saveEventLabel;

    @FXML
    private ListView<Event> allEventsList;

    @FXML
    private TextField searchBar;

    @FXML
    private CheckBox myEventsCheckBox;

    private boolean checkBoxValue = false;

    private User user;

    public AppController(User user) {
        this.user = user;
    }

    /**
     * Reads events from file and displays events in
     * view, sorted after date and time.
     * Also initializes the filtration search field.  
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

        // Initializing search filtration functionality
        SortedList<Event> sortedList = ControllerUtil.searchFiltrator(eventCollection, searchBar);

        allEventsList.setItems(sortedList.filtered(e -> true));
        
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
    private void handleMyEventsCheckBox() {
        setChecked();
        myEventsCheckBox.setSelected(isChecked());
        FilteredList<Event> ev = (FilteredList<Event>) allEventsList.getItems();
        if (isChecked()) {
            Predicate<Event> pred = event -> {
                return event.getUsers().stream()
                        .anyMatch(user -> user.username().equals(this.user.username()));
            };
            ev.setPredicate(pred);
        }
        else {
            ev.setPredicate(e -> true);
        }
    }

    private boolean isChecked() {
        return this.checkBoxValue;
    }

    private void setChecked() {
        this.checkBoxValue = !isChecked();
    }


    @FXML
    private void handleCreateEventButtonClicked() {
        String fxmlFileName = "CreateEvent.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, NewEventController.class, user);
        ControllerUtil.setSceneFromChild(loader, createEventButton);
    }
}