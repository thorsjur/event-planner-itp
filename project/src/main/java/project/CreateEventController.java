package project;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CreateEventController {
    
    @FXML
    private Button CreateEventButton, EventsButton, MyEventsButton;

    @FXML
    private void handleMyEventsButtonClicked() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MyEvents.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEventsButtonClicked(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AllEvents.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateEventButtonClicked(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CreateEvent.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

