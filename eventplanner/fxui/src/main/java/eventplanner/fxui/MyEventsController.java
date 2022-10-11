package eventplanner.fxui;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import eventplanner.json.EventCollectionJsonReader;
import eventplanner.json.EventCollectionJsonWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;


public class MyEventsController {

    @FXML
    private Button createEventButton, eventsButton, myEventsButton;

    @FXML
    private ListView<Event> myEventsList;

    @FXML
    private Label removeEventLabel;

    private User user;

    private Collection<Event> eventCollection;

    /**
     * Loads events to view
     */
    @FXML
    private void handleLoadMyEventsButtonClicked(){
        myEventsList.getItems().clear();
        updateSavedEventsListView();
        if (myEventsList.getItems().size()==0){
            removeEventLabel.setText("You have no events\nsaved. Add events\nfrom 'All Events' page");
        }
    }

    /**
     * Removes user from selected items' list of users, 
     * writes changes to file and removes the selected 
     * events from view  
     */
    @FXML
    private void handleRemoveEventButtonClicked(){
        if (myEventsList.getSelectionModel().getSelectedItem()==null){
            removeEventLabel.setText("No events chosen");
        }
        else{
            Collection<Event> selectedEvents = myEventsList.getSelectionModel().getSelectedItems();
            for (Event event : selectedEvents) {
                event.removeUser(getUser());
            }
            EventCollectionJsonWriter writer = new EventCollectionJsonWriter();
            try {
                writer.save(eventCollection);
            } catch (IOException e) {
                e.printStackTrace();
            }
            myEventsList.getItems().removeAll(selectedEvents);
            myEventsList.setCellFactory(new Callback<ListView<Event>, ListCell<Event>>() {
                @Override
                public ListCell<Event> call(ListView<Event> param) {
                    return new EventCell();
                }
    
            });
            removeEventLabel.setText("Events removed \n from 'My events'");
        }
    }

    @FXML
    private void handleEventsButtonClicked(){
        String pathName = "AllEvents.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoader(pathName);
        ControllerUtil.setSceneFromChild(loader, myEventsButton);
        AppController appController = loader.getController();
        appController.setUser(getUser());
    }

    @FXML
    private void handleCreateEventButtonClicked(){
        String pathName = "CreateEvent.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoader(pathName);
        ControllerUtil.setSceneFromChild(loader, myEventsButton);
        NewEventController newEventController = loader.getController();
        newEventController.setUser(getUser());
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
            e.printStackTrace(); 
        }
        ArrayList<Event> savedEvents = new ArrayList<>();
        for (Event event : eventCollection) {
            for (User user : event.getUsers()) {
                if (user.username().equals(getUser().username())) {
                    savedEvents.add(event);
                }
            }
        }
        Collections.sort(savedEvents, new Comparator<Event>() {

            @Override
            public int compare(Event e1, Event e2) {
                return e1.getStartDate().compareTo(e2.getStartDate());
            }

        });

        myEventsList.getItems().addAll(savedEvents);

        // Makes it possible to choose multiple items in list view by holding ctrl+cmd while selecting items
        myEventsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        myEventsList.setCellFactory(new Callback<ListView<Event>, ListCell<Event>>() {
            @Override
            public ListCell<Event> call(ListView<Event> param) {
                return new EventCell();
            }

        });
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User getUser() {
        return this.user;
    }
}

