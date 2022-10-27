package eventplanner.fxui;

import java.io.File;
import java.util.Collection;
import java.util.function.Predicate;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
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
    private Button createEventButton, saveEventButton, logOutButton;

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

        // Using a series of transformations to filter using the search field, then
        // sorting using a comparator. The final FilteredList is used to filter by
        // events you're either registered to or not (see UpdateListViewPredicate()).
        FilteredList<Event> filteredList = ControllerUtil.searchFiltrator(observableList, searchBar);
        SortedList<Event> sortedList = filteredList.sorted(ControllerUtil.getDateComparator());
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
     * users and writes changes to file.
     */
    @FXML
    private void handleSaveEventButtonClicked() {
        if (allEventsList.getSelectionModel().getSelectedItem() != null) {
            ObservableList<Event> selectedEvents = allEventsList
                    .getSelectionModel()
                    .getSelectedItems();

            handleRegistrationOrDeregistration(selectedEvents, null);

        } else {
            saveEventLabel.setText("No events chosen");
        }
    }

    @FXML
    private void handleMyEventsCheckBox() {
        toggleIsChecked();
        myEventsCheckBox.setSelected(isChecked());
        updateRegistrationLabel();
        updateListViewPredicate();
    }

    private void handleRegistrationOrDeregistration(ObservableList<Event> selectedEvents, File file) {
        if (checkBoxIsChecked) {
            deregisterSelectedUser(selectedEvents, file);
        } else {
            registerSelectedUser(selectedEvents, file);
        }
        updateListViewPredicate();
    }

    private void deregisterSelectedUser(ObservableList<Event> selectedEvents, File file) {
        selectedEvents.forEach(event -> event.removeUser(user));
        DataAccess.updateEvents(selectedEvents);

        saveEventLabel.setText("Deregistration successful");
    }

    private void registerSelectedUser(ObservableList<Event> selectedEvents, File file) {
        selectedEvents.forEach(event -> event.addUser(user));
        DataAccess.updateEvents(selectedEvents);

        saveEventLabel.setText("Registration successful");
    }

    private void updateListViewPredicate() {
        Predicate<Event> pred;
        if (checkBoxIsChecked) {
            pred = event -> {
                return event.getUsers().stream()
                        .anyMatch(user -> user.email().equals(this.user.email()));
            };
        } else {
            // shows all events
            pred = event -> true;
        }

        FilteredList<Event> underlyingFilteredList = (FilteredList<Event>) allEventsList.getItems();
        underlyingFilteredList.setPredicate(pred);
    }

    private void updateRegistrationLabel() {
        saveEventButton.setText(checkBoxIsChecked ? "Deregister" : "Register");
    }

    private boolean isChecked() {
        return checkBoxIsChecked;
    }

    private void toggleIsChecked() {
        this.checkBoxIsChecked = !checkBoxIsChecked;
    }

    private ObservableList<Event> loadEvents() {
        Collection<Event> eventCollection = DataAccess.getAllEvents();
        return FXCollections.observableArrayList(eventCollection);
    }

    @FXML
    private void handleCreateEventButtonClicked() {
        String fxmlFileName = "CreateEvent.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, NewEventController.class, user);
        ControllerUtil.setSceneFromChild(loader, createEventButton);
    }

    @FXML
    private void handleLogOutButtonClicked() {
        String fxmlFileName = "LoginScreen.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoader(fxmlFileName);
        ControllerUtil.setSceneFromChild(loader, logOutButton);   
    }
}