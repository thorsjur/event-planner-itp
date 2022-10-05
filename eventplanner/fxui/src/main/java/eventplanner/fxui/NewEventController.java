package eventplanner.fxui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import eventplanner.core.Event;
import eventplanner.core.EventType;
import eventplanner.core.util.Validation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import eventplanner.json.util.IOUtil;

public class NewEventController {
    
    @FXML
    private Button CreateEventButton, EventsButton, MyEventsButton;

    @FXML
    private DatePicker startDatePicker, endDatePicker;

    @FXML
    private TextField startTimeField, endTimeField, nameField, descField, locationField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private Text outputMessage;

    @FXML
    public void initialize() {
        for (EventType eventType : EventType.values()) {
            typeComboBox.getItems().add(eventType.toString());
        }
    }

    @FXML
    private void handleCreateNewEventButton() {
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();
        if (!Validation.isValidTimeString(startTime)) handleInvalidTextField(startTimeField);
        if (!Validation.isValidTimeString(endTime)) handleInvalidTextField(endTimeField);

        LocalDateTime localDateTimeStart = getLocalDateTimeObject(startTime, startDatePicker.getValue());
        LocalDateTime localDateTimeEnd = getLocalDateTimeObject(endTime, endDatePicker.getValue());

        String name = nameField.getText();
        if (name == null || name.length() < 3) handleInvalidTextField(nameField);

        String location = locationField.getText();
        if (location == null || location.length() < 2) handleInvalidTextField(locationField);

        EventType eventType = EventType.valueOf(typeComboBox.getValue());

        List<String> users = new ArrayList<>();

        Event event = new Event(eventType, name, localDateTimeStart, localDateTimeEnd, location, users);
        try {
            IOUtil.appendEventToFile(event, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        resetFields();
        outputMessage.setText("New event created successfully");
    }

    private void handleInvalidTextField(TextField textField) {
        textField.setStyle("-fx-text-box-border: #B33333;");
        outputMessage.setText("Invalid field entered: " + textField.getText());
        throw new IllegalArgumentException("Invalid time: " + textField.getText());
    }

    /**
     * NOTE: Assumes validated input
     * @param startTime
     * @return An array of length 2 containing hour and minutes
     */
    private int[] getTimeAsArray(String time) {
        String[] split = time.split(":");
        return new int[] { Integer.parseInt(split[0]), Integer.parseInt(split[1]) };
    }

    private LocalDateTime getLocalDateTimeObject(String time, LocalDate date) {
        int[] timeArray = getTimeAsArray(time);
        int hour = timeArray[0];
        int minute = timeArray[1];

        return date.atTime(hour, minute);
    }

    private void resetFields() {
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);

        startTimeField.clear();
        endTimeField.clear();
        nameField.clear();
        descField.clear();
        locationField.clear();

        typeComboBox.valueProperty().set(null);
        outputMessage.setText("");
    }

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

