package eventplanner.fxui;

import java.io.IOException;
import java.util.Collection;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import eventplanner.json.EventCollectionJsonReader;
import eventplanner.json.EventCollectionJsonWriter;
import eventplanner.json.util.IOUtil;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

public class EventPageController {

    @FXML
    private Button returnButton, registerButton, deleteEventButton;

    @FXML
    private Label nameLabel, authorLabel, startTimeLabel, endTimeLabel, locationLabel, regUsersLabel;

    @FXML
    private Text descText, outputText;

    @FXML
    private ScrollPane scrollPane;

    private User user;
    private Event event;
    private boolean isRegistered;

    public EventPageController(User user, Event event) {
        this.user = user;
        this.event = event;
        this.isRegistered = event.getUsers().contains(user);
    }

    @FXML
    private void initialize() {
        initializeDescription();
        initializeTextLabels();
        initializeOutputText();
        initializeRegisterButton();
        initializeDeleteEventButton();
    }

    @FXML
    private void handleReturnBtnClicked() {
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory("AllEvents.fxml", AllEventsController.class, user);
        ControllerUtil.setSceneFromChild(loader, returnButton);
    }

    @FXML
    private void handleDeleteEventButtonClicked() {
        ButtonType deleteType = new ButtonType("Delete Event", ButtonData.OK_DONE);
        Alert alert = new Alert(
            AlertType.CONFIRMATION,
            "Are you sure you want to delete this event?",
            deleteType,
            ButtonType.CANCEL
        );
        alert.setGraphic(null);
        alert.setHeaderText(null);
        alert.showAndWait();

        if (alert.getResult().getButtonData() == ButtonData.OK_DONE) {
            ControllerUtil.deleteEventFromFile(this.event);
            handleReturnBtnClicked();
        }
    }

    private void initializeRegisterButton() {
        updateRegisterButton();
        String buttonText = isRegistered ? "Deregister" : "Register";
        registerButton.setText(buttonText);
    }

    private void updateRegisterButton() {
        EventHandler<MouseEvent> handler;
        if (!isRegistered) {
            handler = (e) -> {
                handleRegisterEventBtnClicked();
                registerButton.setText("Deregister");
            };
        } else {
            handler = (e) -> {
                handleDeregisterEventBtnClicked();
                registerButton.setText("Register");
            };
        }
        registerButton.setOnMouseClicked(handler);
    }

    private void handleRegisterEventBtnClicked() {
        try {
            IOUtil.addUserToEvent(event, user, null);
        } catch (IOException e) {
            outputText.setText("An error occurred while trying to register user");
            return;
        }
        outputText.setText("You have successfully registered to " + event.getName());
        
        incrementRegisteredUsers();
        isRegistered = true;
        updateRegisterButton();
    }

    private void handleDeregisterEventBtnClicked() {
        try {
            IOUtil.removeUserFromEvent(event, user, null);
        } catch (IOException e) {
            outputText.setText("An error occurred while trying to deregister user");
            return;
        }
        outputText.setText("You have successfully deregistered from " + event.getName());

        decrementRegisteredUsers();
        isRegistered = false;
        updateRegisterButton();
    }

    private void initializeDeleteEventButton() {
        if (!user.email().equals(event.getAuthorEmail())) { // TODO: should compare to author/owner of event
            deleteEventButton.setDisable(true);
            deleteEventButton.setVisible(false);
        };
    }

    private void initializeDescription() {
        descText.wrappingWidthProperty().bind(scrollPane.widthProperty().add(-25));
        descText.setText(event.getDescription());
    }

    private void initializeTextLabels() {
        // nameLabel, authorLabel, startTimeLabel, endTimeLabel, locationLabel, regUsersLabel;
        nameLabel.setText(event.getName());
        authorLabel.setText(event.getAuthorEmail()); 
        startTimeLabel.setText(event.getStartDate().toString().replace("T", " "));
        endTimeLabel.setText(event.getEndDate().toString().replace("T", " "));
        locationLabel.setText(event.getLocation());
        updateRegisteredUsers(event.getUsers().size());
    }

    private void updateRegisteredUsers(int n) {
        regUsersLabel.setText(String.valueOf(n));
    }

    private void incrementRegisteredUsers() {
        int n = Integer.parseInt(regUsersLabel.getText());
        updateRegisteredUsers(n + 1);
    }

    private void decrementRegisteredUsers() {
        int n = Integer.parseInt(regUsersLabel.getText());
        updateRegisteredUsers(n - 1);
    }

    private void initializeOutputText() {
        outputText.setText("");
    }
    
}
