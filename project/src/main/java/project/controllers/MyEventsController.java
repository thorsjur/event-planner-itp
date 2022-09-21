package project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MyEventsController {
    
    @FXML
    private Button CreateEventButton, EventsButton, MyEventsButton;

    @FXML
    public void initialize() {
        
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

