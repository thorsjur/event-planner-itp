package eventplanner.fxui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.function.Supplier;

import eventplanner.core.Event;
import eventplanner.core.EventType;
import eventplanner.fxui.util.ControllerUtil;
import eventplanner.fxui.util.InputType;
import eventplanner.fxui.util.Validation;
import eventplanner.json.util.IOUtil;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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

            startTimeField.focusedProperty().addListener(getValidationListener(startTimeField, InputType.TIME));
            endTimeField.focusedProperty().addListener(getValidationListener(endTimeField, InputType.TIME));
            nameField.focusedProperty().addListener(getValidationListener(nameField, InputType.NAME));
            descField.focusedProperty().addListener(getValidationListener(descField, InputType.DESCRIPION));
            locationField.focusedProperty().addListener(getValidationListener(locationField, InputType.LOCATION));
            startDatePicker.focusedProperty().addListener(getValidationListener(startDatePicker, InputType.DATE));
            endDatePicker.focusedProperty().addListener(getValidationListener(endDatePicker, InputType.DATE));
        }
    }

    @FXML
    private void handleCreateNewEventButton() {
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();

        LocalDateTime localDateTimeStart = getLocalDateTimeObject(startTime, startDatePicker.getValue());
        LocalDateTime localDateTimeEnd = getLocalDateTimeObject(endTime, endDatePicker.getValue());

        String name = nameField.getText();

        String location = locationField.getText();

        EventType eventType = EventType.valueOf(typeComboBox.getValue());

        Event event = new Event(eventType, name, localDateTimeStart, localDateTimeEnd, location);
        try {
            IOUtil.appendEventToFile(event, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        resetFields();
        outputMessage.setText("New event created successfully");
    }

    /**
     * NOTE: Assumes validated input
     * 
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
        ControllerUtil.setSceneFromChild("MyEvents.fxml", MyEventsButton);
    }

    @FXML
    private void handleEventsButtonClicked() {
        ControllerUtil.setSceneFromChild("AllEvents.fxml", MyEventsButton);
    }

    @FXML
    private void handleCreateEventButtonClicked() {
        ControllerUtil.setSceneFromChild("CreateEvent.fxml", MyEventsButton);
    }

    private static final String COLOUR_VALID = "#228C22";
    private static final String COLOUR_INVALID = "#B33333";

    private void handleInvalidTextField(TextField textField) {
        textField.setStyle("-fx-text-box-border: " + COLOUR_INVALID);
    }

    private void handleInvalidDatePicker(DatePicker datePicker) {
        datePicker.setStyle("-fx-border-color: " + COLOUR_INVALID);
    }

    private void handleValidTextField(TextField textField) {
        textField.setStyle("-fx-text-box-border: " + COLOUR_VALID);
    }

    private void handleValidDatePicker(DatePicker datePicker) {
        datePicker.setStyle("-fx-border-color: " + COLOUR_VALID);
    }


    private ChangeListener<Boolean> getValidationListener(Control control, InputType type) {
        validateArguments(control, type);

        switch (type) { // All text inputs are handled by the default case
            case DATE:
                DatePicker datePicker = (DatePicker) control;
                return ControllerUtil.getValidationFocusListener(
                    () -> {return Validation.isValidDateInput(datePicker.getValue());},
                    () -> handleValidDatePicker(datePicker),
                    () -> handleInvalidDatePicker(datePicker));
            case EVENT_TYPE:
                return null;
            default:
                return getTextFieldValidationListener((TextField) control, type);
        }
    }

    private ChangeListener<Boolean> getTextFieldValidationListener(TextField field, InputType type) {
        return ControllerUtil.getValidationFocusListener(
            () -> {return Validation.isValidTextInput(field.getText(), type);},
            () -> handleValidTextField(field),
            () -> handleInvalidTextField(field));
    }

    private void validateArguments(Control control, InputType type) {
        if (control == null || type == null) {
            throw new IllegalArgumentException("Null inputs are not permitted");
        }
        Set<InputType> textInputs = Set.of(InputType.DESCRIPION, InputType.LOCATION, InputType.NAME, InputType.TIME);
        if (!(control instanceof TextField) && textInputs.contains(type)) {
            throw new IllegalArgumentException("Only text fields support this input type.");
        } else if (!(control instanceof DatePicker) && type == InputType.DATE) {
            throw new IllegalArgumentException("Only date pickers support this input type.");
        } else if (!(control instanceof ComboBox) && type == InputType.EVENT_TYPE) {
            throw new IllegalArgumentException("Only combo boxes support this input type.");
        }
    }
}
