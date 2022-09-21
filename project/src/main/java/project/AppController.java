package project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class AppController {
    
    @FXML
    private Button CreateEventButton, EventsButton, MyEventsButton;

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

