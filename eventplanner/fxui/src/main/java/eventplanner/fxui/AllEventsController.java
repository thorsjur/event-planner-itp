package eventplanner.fxui;

import java.util.Collection;
import java.util.function.Predicate;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.dataaccess.DataAccess;
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
    private ListView<Event> eventsListView;

    @FXML
    private TextField searchBar;

    @FXML
    private CheckBox myEventsCheckBox;

    private boolean checkBoxIsChecked = false;
    private DataAccess dataAccess;
    private User user;

    public AllEventsController(User user, DataAccess dataAccess) {
        this.user = user;
        this.dataAccess = dataAccess.copy();
    }

    /**
     * Reads events from file and displays events in view, sorted after date and
     * time. Also initializes the filtration search field.
     */
    @FXML
    private void initialize() {

        ObservableList<Event> observableList = loadEvents();

        // Using a series of transformations to filter using the search field, then
        // sorting using a comparator. The final FilteredList is used to filter by
        // events you're either registered to or not (see UpdateListViewPredicate()).
        FilteredList<Event> filteredList = ControllerUtil.searchFiltrator(observableList, searchBar);
        SortedList<Event> sortedList = filteredList.sorted(ControllerUtil.getDateComparator());
        FilteredList<Event> myEventsList = sortedList.filtered(e -> true);

        eventsListView.setItems(myEventsList);

        // Makes it possible to choose multiple items in list view
        // by holding ctrl+cmd while selecting items
        eventsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Sets the CellFactory for the listview to produce EventCells (custom cells)
        eventsListView.setCellFactory(param -> new EventCell(dataAccess));

        // Supplies the user to the listview, so the event cells have access to the user
        // object.
        eventsListView.setUserData(user);
    }

    /**
     * Adds user to selected events' lists of users and writes changes to file.
     */
    @FXML
    private void handleSaveEventButtonClicked() {
        if (eventsListView.getSelectionModel().getSelectedItem() != null) {
            ObservableList<Event> selectedEvents = eventsListView.getSelectionModel().getSelectedItems();
            handleRegistrationOrDeregistration(selectedEvents);

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

    private void handleRegistrationOrDeregistration(ObservableList<Event> selectedEvents) {
        if (checkBoxIsChecked) {
            deregisterSelectedUser(selectedEvents);
        } else {
            registerSelectedUser(selectedEvents);
        }
        updateListViewPredicate();
    }

    private void deregisterSelectedUser(ObservableList<Event> selectedEvents) {
        selectedEvents.forEach(event -> event.removeUser(user));
        if (dataAccess.updateEvents(selectedEvents)) {
            saveEventLabel.setText("Deregistration successful");
        } else {
            saveEventLabel.setText(ControllerUtil.SERVER_ERROR);
        }
    }

    private void registerSelectedUser(ObservableList<Event> selectedEvents) {
        selectedEvents.forEach(event -> event.addUser(user));
        if (dataAccess.updateEvents(selectedEvents)) {
            saveEventLabel.setText("Registration successful");
        } else {
            saveEventLabel.setText(ControllerUtil.SERVER_ERROR);
        }
    }

    private void updateListViewPredicate() {
        Predicate<Event> pred;
        if (checkBoxIsChecked) {
            pred = event -> event.getUsers().stream().anyMatch(u -> u.email().equals(user.email()));
        } else {

            // shows all events
            pred = event -> true;
        }

        FilteredList<Event> underlyingFilteredList = (FilteredList<Event>) eventsListView.getItems();
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
        Collection<Event> eventCollection = dataAccess.getAllEvents();
        if (eventCollection == null) {
            saveEventLabel.setText(ControllerUtil.SERVER_ERROR);
            return null;
        }
        
        return FXCollections.observableArrayList(eventCollection);
    }

    @FXML
    private void handleCreateEventButtonClicked() {
        String fxmlFileName = "CreateEvent.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, NewEventController.class, user,
                dataAccess);
        ControllerUtil.setSceneFromChild(loader, createEventButton);
    }

    @FXML
    private void handleLogOutButtonClicked() {
        String fxmlFileName = "LoginScreen.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, LoginController.class, null,
                dataAccess);
        ControllerUtil.setSceneFromChild(loader, logOutButton);
    }
}