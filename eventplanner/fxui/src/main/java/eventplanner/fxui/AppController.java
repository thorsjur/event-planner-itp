package eventplanner.fxui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private Button createEventButton, myEventsButton;

    @FXML
    private Label saveEventLabel;

    @FXML
    private ListView<Event> allEventsList;

    private User user;

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
                return e1.getStartDate().compareTo(e2.getStartDate());
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
        if (allEventsList.getSelectionModel().getSelectedItem()!=null){
            ObservableList<Event> selectedEvents =  allEventsList.getSelectionModel().getSelectedItems();
            for (Event event : selectedEvents) {
                event.addUser(getUser());
            }
            EventCollectionJsonWriter reader = new EventCollectionJsonWriter();
            try {
                reader.save(allEventsList.getItems());
                saveEventLabel.setText("Events saved \n to 'My events'");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        else{
            saveEventLabel.setText("No events chosen");
        }
    }

    @FXML
    private void handleMyEventsButtonClicked() {
        String pathName = "MyEvents.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoader(pathName);
        ControllerUtil.setSceneFromChild(loader, myEventsButton);
        MyEventsController myEventsController = loader.getController();
        myEventsController.setUser(getUser());
    }

    @FXML
    private void handleCreateEventButtonClicked(){
        String pathName = "CreateEvent.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoader(pathName);
        ControllerUtil.setSceneFromChild(loader, myEventsButton);
        NewEventController newEventController = loader.getController();
        newEventController.setUser(getUser());
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User getUser() {
        return this.user;
    }
}



