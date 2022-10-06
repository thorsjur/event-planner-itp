package eventplanner.fxui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import eventplanner.core.Event;
import eventplanner.core.EventType;
import eventplanner.core.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;
import eventplanner.json.EventCollectionJsonReader;
import eventplanner.json.EventCollectionJsonWriter;

public class AppController {
    
    @FXML
    private Button CreateEventButton, EventsButton, MyEventsButton;

    @FXML
    private Label saveEventLabel;

    @FXML
    private ListView<Event> allEventsList;

    static User user = new User("user1");

    @FXML
    public void initialize() {
        
        EventCollectionJsonReader reader = new EventCollectionJsonReader();
        Collection<Event> eventCollection;
        try {
            eventCollection = reader.load();
        } catch (IOException e) {
            eventCollection = new ArrayList<>();
            e.printStackTrace(); 
        }
        ArrayList<Event> sortedEvents = new ArrayList<>(eventCollection);
        Collections.sort(sortedEvents, new Comparator<Event>() {

            @Override
            public int compare(Event e1, Event e2) {
                return e2.getStartDate().compareTo(e1.getStartDate());
            }

        });
        allEventsList.getItems().addAll(sortedEvents);

        allEventsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Makes it possible to choose multiple items in list view by holding ctrl+cmd after clicked on first item
        
        allEventsList.setCellFactory(new Callback<ListView<Event>, ListCell<Event>>() {
            @Override
            public ListCell<Event> call(ListView<Event> param) {
                return new EventCell();
            }

        });
    }

    @FXML
    private void handleSaveEventButtonClicked(){
        if (!allEventsList.isPressed()){
            saveEventLabel.setText("No events chosen");
        }
        ObservableList<Event> selectedEvents =  allEventsList.getSelectionModel().getSelectedItems();
        for (Event event : selectedEvents) {
            event.addUser(user);
        }
        EventCollectionJsonWriter reader = new EventCollectionJsonWriter();
        try {
            reader.save(allEventsList.getItems());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        saveEventLabel.setText("Events saved \n to 'My events'");
    }

    @FXML
    private void handleMyEventsButtonClicked() {
        ControllerUtil.setSceneFromChild( "MyEvents.fxml", MyEventsButton);
    }

    @FXML
    private void handleEventsButtonClicked(){
        ControllerUtil.setSceneFromChild( "AllEvents.fxml", MyEventsButton);
    }

    @FXML
    private void handleCreateEventButtonClicked(){
        ControllerUtil.setSceneFromChild( "CreateEvent.fxml", MyEventsButton);
    }
}



