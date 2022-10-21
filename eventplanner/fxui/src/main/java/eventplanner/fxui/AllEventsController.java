package eventplanner.fxui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import eventplanner.json.EventCollectionJsonReader;
import eventplanner.json.util.IOUtil;
import javafx.collections.FXCollections;
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
public class AllEventsController {

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

    private ObservableList<Event> observableList;
    private boolean checkBoxIsChecked = false;
    private User user;

    public AllEventsController(User user) {
        this.user = user;
    }

    /**
     * Reads events from file and displays events in
     * view, sorted after date and time.
     * Also initializes the filtration search field.  
     */
    @FXML
    private void initialize() {
        
        observableList = loadEvents();

        // Initializing search filtration functionality
        FilteredList<Event> filteredList = ControllerUtil.searchFiltrator(observableList, searchBar);
        SortedList<Event> sortedList = filteredList.sorted(ControllerUtil.getReverseDateComparator());
        FilteredList<Event> myEventsList = sortedList.filtered(e -> true);

        allEventsList.setItems(myEventsList);
        
        // Makes it possible to choose multiple items in list view
        // by holding ctrl+cmd while selecting items
        allEventsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Sets the CellFactory for the listview to produce EventCells (custom cells)
        allEventsList.setCellFactory((param) -> new EventCell());

        allEventsList.setUserData(user);
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

            try {
                IOUtil.addUserToEvents(selectedEvents, user, null);
                saveEventLabel.setText("Registration successful");
            } catch (IOException e) {
                System.out.println("Could not register to events ...");
                return;
            }

        } else {
            saveEventLabel.setText("No events chosen");
        }
    }

    @FXML
    private void handleMyEventsCheckBox() {
        toggleIsChecked();
        myEventsCheckBox.setSelected(isChecked());

        Predicate<Event> pred;
        if (checkBoxIsChecked) {
            pred = event -> {
                return event.getUsers().stream()
                        .anyMatch(user -> user.email().equals(this.user.email()));
            };
        } else {
            pred = event -> true;
        }

        FilteredList<Event> ev = (FilteredList<Event>) allEventsList.getItems();
        ev.setPredicate(pred);
    }

    private boolean isChecked() {
        return checkBoxIsChecked;
    }

    private void toggleIsChecked() {
        this.checkBoxIsChecked = !checkBoxIsChecked;
    }

    private ObservableList<Event> loadEvents() {
        Collection<Event> eventCollection;
        
        try {
            EventCollectionJsonReader reader = new EventCollectionJsonReader();
            eventCollection = reader.load();
        } catch (IOException e) {
            eventCollection = new ArrayList<>();
            System.out.println("Could not load events");
        }

        return FXCollections.observableArrayList(eventCollection);
    }

    @FXML
    private void handleCreateEventButtonClicked() {
        String fxmlFileName = "CreateEvent.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, NewEventController.class, user);
        ControllerUtil.setSceneFromChild(loader, createEventButton);
    }
}