package eventplanner.fxui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import eventplanner.core.Event;
import eventplanner.fxui.util.ControllerUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;


import eventplanner.json.EventCollectionJsonReader;

public class AppController {
    private ControllerUtil utils = new ControllerUtil();
    @FXML
    private Button CreateEventButton, EventsButton, MyEventsButton;

    @FXML
    private ListView<Event> allEventsList;

    @FXML
    private TextField textField;

    @FXML
    private Stage loginStage;

    @FXML
    public void initialize() {
        setInputUsernameStage();

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
                return e2.getStartDate().compareTo(e1.getStartDate());
            }

        });
        allEventsList.getItems().addAll(sortedEvents);
        
        allEventsList.setCellFactory(new Callback<ListView<Event>, ListCell<Event>>() {
            @Override
            public ListCell<Event> call(ListView<Event> param) {
                return new EventCell();
            }

        });
    }

    @FXML
    private void setInputUsernameStage() {
        Label label1 = new Label("Input username:");
        textField = new TextField("user" + utils.randNumGenerator());
        HBox hb = new HBox();
        Button btnConfirm = new Button("Confirm");
        Button btnCancel = new Button("Cancel");
        btnCancel.setOnMouseClicked(handleCancel);
        btnConfirm.setOnAction(handleConfirm);
        hb.getChildren().addAll(label1, textField, btnConfirm, btnCancel);
        hb.setSpacing(10);
        BorderPane pane = new BorderPane();
        pane.setCenter(hb);
        pane.setPadding(new Insets(20,20,20,20));
        Scene scene = new Scene(pane, 500, 100); 		                        
        loginStage = new Stage(); 		                        
        loginStage.setScene(scene); 		                        
        loginStage.setTitle("Login");
        loginStage.setAlwaysOnTop(true);
        loginStage.show(); 
    }

    @FXML
    private EventHandler<MouseEvent> handleCancel = new EventHandler<MouseEvent>() { 
        @Override
        public void handle(MouseEvent event) {
            Platform.exit();
        }
    };

    @FXML
    private EventHandler<ActionEvent> handleConfirm = new EventHandler<ActionEvent>() { 
        @Override
        public void handle(ActionEvent event) {
            String username = textField.getText();
            System.out.println(username);
            loginStage.close();
        }
    };

    @FXML
    private void handleMyEventsButtonClicked() {
        ControllerUtil.setSceneFromChild( "MyEvents.fxml", MyEventsButton);
    }

    @FXML
    private void handleEventsButtonClicked(){
        ControllerUtil.setSceneFromChild( "AllEvents.fxml", MyEventsButton);
    }

    @FXML
    private void handleCreateEventButtonClicked(){
        ControllerUtil.setSceneFromChild( "CreateEvent.fxml", MyEventsButton);
    }
}

