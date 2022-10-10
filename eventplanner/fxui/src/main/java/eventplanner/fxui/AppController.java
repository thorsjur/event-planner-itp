package eventplanner.fxui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import eventplanner.core.Event;
import eventplanner.core.EventType;
import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
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
import eventplanner.json.EventCollectionJsonWriter;

public class AppController {
    private ControllerUtil utils = new ControllerUtil();
    @FXML
    private Button createEventButton, eventsButton, myEventsButton;

    @FXML
    private Label saveEventLabel;

    @FXML
    private ListView<Event> allEventsList;

    @FXML
    private TextField textField;

    @FXML
    private Stage loginStage;

    static User user = new User("user1");

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
        if (!allEventsList.isPressed()){
            saveEventLabel.setText("No events chosen");
        }
        ObservableList<Event> selectedEvents =  allEventsList.getSelectionModel().getSelectedItems();
        for (Event event : selectedEvents) {
            event.addUser(user);
        }
        EventCollectionJsonWriter reader = new EventCollectionJsonWriter();
        try {
            reader.save(allEventsList.getItems());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        saveEventLabel.setText("Events saved \n to 'My events'");
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



