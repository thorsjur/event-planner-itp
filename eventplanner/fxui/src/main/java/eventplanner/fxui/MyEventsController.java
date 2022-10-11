package eventplanner.fxui;
import java.io.IOException;
import java.util.Collection;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import eventplanner.json.EventCollectionJsonReader;
import eventplanner.json.EventCollectionJsonWriter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;


public class MyEventsController {

    @FXML
    private Button createEventButton, eventsButton, myEventsButton;

    @FXML
    private ListView<Event> savedEventsList;

    @FXML
    private Label removeEventLabel;

    private User user;

    @FXML
    public void initialize() {
        try {
            updateSavedEventsListView();
            savedEventsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSavedEventsListView(){
        ObservableList<Event> favoriteEvents = savedEventsList.getItems();
        if (favoriteEvents == null){
            removeEventLabel.setText("No favorite events yet");
        }
        else{
            EventCollectionJsonReader reader = new EventCollectionJsonReader();
            Collection<Event> allEvents;
            try {
                allEvents = reader.load();
                for (int i = 0; i < allEvents.size(); i++){
                    for (Event event : allEvents) {
                        if (getUser().username().equals(event.getUsers().get(i).username())){
                            favoriteEvents.add(event);
                        }
                    }
                }
                savedEventsList.setItems(favoriteEvents);
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }
    }

    @FXML
    public void handleRemoveEventButtonClicked(){
        if (!savedEventsList.isPressed()){
            removeEventLabel.setText("No events chosen");
        }
        else{
            Collection<Event> selectedEvents = savedEventsList.getSelectionModel().getSelectedItems();
            for (Event event : selectedEvents) {
                event.removeUser(getUser());
            }
            EventCollectionJsonWriter writer = new EventCollectionJsonWriter();
            try {
                writer.save(selectedEvents);
            } catch (IOException e) {
                e.printStackTrace();
            }
            savedEventsList.getItems().removeAll(selectedEvents);
            updateSavedEventsListView();
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

    public void setUser(User user) {
        this.user = user;
    }

    private User getUser() {
        return this.user;
    }
}

