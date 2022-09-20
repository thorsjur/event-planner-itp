package project;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AppController {
    
    private Button CreateEventButton, EventsButton, MyEventsButton;

    @FXML
    private void handleMyEventsButtonClicked(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/App.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @FXML
    private void handleEventsButtonClicked(){
        
    }

    @FXML
    private void handleCreateEventButtonClicked(){
        
    }
}

