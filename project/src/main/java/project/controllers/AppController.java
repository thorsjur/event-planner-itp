package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import json.EventCollectionJsonReader;

public class AppController {
    
    @FXML
    private Button CreateEventButton, EventsButton, MyEventsButton;

    @FXML
    private ListView<Event> allEventsList;

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

        eventCollection.forEach(e -> {
            allEventsList.getItems().add(e);
        });
        
        allEventsList.setCellFactory(new Callback<ListView<Event>, ListCell<Event>>() {
            @Override
            public ListCell<Event> call(ListView<Event> param) {
                return new EventCell();
            }

        });
    }

    @FXML
    private void handleMyEventsButtonClicked() {
        ControllerUtil.setSceneFromChild( "/MyEvents.fxml", MyEventsButton);
    }

    @FXML
    private void handleEventsButtonClicked(){
        ControllerUtil.setSceneFromChild( "/AllEvents.fxml", MyEventsButton);
    }

    @FXML
    private void handleCreateEventButtonClicked(){
        ControllerUtil.setSceneFromChild( "/CreateEvent.fxml", MyEventsButton);
    }
}

