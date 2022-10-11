package eventplanner.fxui;

import eventplanner.fxui.util.ControllerUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MyEventsController {
    
    @FXML
    private Button createEventButton, eventsButton, myEventsButton;

    @FXML
    public void initialize() {
        
    }

    @FXML
    private void handleMyEventsButtonClicked() {
        ControllerUtil.setSceneFromChild( "MyEvents.fxml", myEventsButton);
    }

    @FXML
    private void handleEventsButtonClicked(){
        ControllerUtil.setSceneFromChild( "AllEvents.fxml", myEventsButton);
    }

    @FXML
    private void handleCreateEventButtonClicked(){
        ControllerUtil.setSceneFromChild( "CreateEvent.fxml", myEventsButton);
    }
}

