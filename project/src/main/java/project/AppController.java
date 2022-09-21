package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    private int view;

    @FXML
    public void initialize() {
        if (view != 0) return;

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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MyEvents.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            MyEventsButton.getScene().setRoot(fxmlLoader.getRoot());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEventsButtonClicked(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AllEvents.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            MyEventsButton.getScene().setRoot(fxmlLoader.getRoot());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateEventButtonClicked(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CreateEvent.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            MyEventsButton.getScene().setRoot(fxmlLoader.getRoot());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

