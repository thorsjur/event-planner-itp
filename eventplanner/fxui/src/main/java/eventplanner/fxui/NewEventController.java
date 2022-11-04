package eventplanner.fxui;

import eventplanner.core.User;
import eventplanner.core.Event;
import eventplanner.core.EventType;
import eventplanner.fxui.util.ControllerUtil;
import eventplanner.fxui.util.InputType;
import eventplanner.fxui.util.Validation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Controller for new events.
 */
public class NewEventController {

    @FXML
    private Button eventsButton, logOutButton;

    @FXML
    private DatePicker startDatePicker, endDatePicker;

    @FXML
    private TextField startTimeField, endTimeField, nameField, descField, locationField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextArea outputMessage;
    
    private User user;
    private DataAccess dataAccess;

    public NewEventController(User user, DataAccess dataAccess) {
        this.user = user;
        this.dataAccess = dataAccess;
    }

    @FXML
    private void initialize() {
        Stream.of(EventType.values())
                .map(eventType -> eventType.toString())
                .forEach(typeComboBox.getItems()::add);

        // Adds listeners to all input fields that signals input-validity to the user.
        ControllerUtil.addValidationFocusListener(endTimeField, InputType.TIME);
        ControllerUtil.addValidationFocusListener(nameField, InputType.NAME);
        ControllerUtil.addValidationFocusListener(locationField, InputType.LOCATION);
        ControllerUtil.addValidationFocusListener(startTimeField, InputType.TIME);
        ControllerUtil.addValidationFocusListener(startDatePicker, InputType.DATE);
        ControllerUtil.addValidationFocusListener(endDatePicker, InputType.DATE);
    }

    @FXML
    private void handleCreateNewEventButton() {
        ArrayList<Validation.ErrorType> errors = new ArrayList<>();

        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();
        if (!Validation.isValidTextInput(startTime, InputType.TIME)
                || !Validation.isValidTextInput(endTime, InputType.TIME)) {
            errors.add(Validation.ErrorType.INVALID_TIME);
        }

        String name = nameField.getText();
        if (!Validation.isValidTextInput(name, InputType.NAME)) {
            errors.add(Validation.ErrorType.INVALID_NAME);
        }

        String location = locationField.getText();
        if (!Validation.isValidTextInput(location, InputType.LOCATION)) {
            errors.add(Validation.ErrorType.INVALID_LOCATION);
        }

        String eventString = typeComboBox.getValue();
        EventType eventType = null;
        if (!Validation.isValidEventType(eventString)) {
            errors.add(Validation.ErrorType.INVALID_TYPE);
        } else {
            eventType = EventType.valueOf(eventString);
        }

        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        if (!Validation.areValidDateInputs(startDate, endDate)) {
            errors.add(Validation.ErrorType.INVALID_DATE);
        }

        if (!Validation.isStartBeforeEnd(startDate, startTime, endDate, endTime)) {
            errors.add(Validation.ErrorType.INVALID_TIME_RELATIONSHIP);
        }

        if (errors.size() > 0) {
            displayErrorMessages(errors);
            return;
        }

        String description = descField.getText();

        String authorEmail = user.email();

        LocalDateTime localDateTimeStart = getLocalDateTimeObject(startTime, startDate);
        LocalDateTime localDateTimeEnd = getLocalDateTimeObject(endTime, endDate);
        Event event = new Event(
                null,
                eventType,
                name,
                localDateTimeStart,
                localDateTimeEnd,
                location,
                null,
                authorEmail,
                description);

        boolean saveFlag = dataAccess.createEvent(event);

        resetFields();
        String outputMessageString = saveFlag
                ? "New event created successfully"
                : ControllerUtil.SERVER_ERROR;
        outputMessage.setText(outputMessageString);
    }

    private void displayErrorMessages(ArrayList<Validation.ErrorType> errors) {
        StringBuilder sb = new StringBuilder();
        errors.forEach(e -> {
            sb.append(e.errMessage + "\n");
        });
        outputMessage.setText(sb.toString());
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

    private LocalDateTime getLocalDateTimeObject(String time, LocalDate date) {
        if (!Validation.isValidTextInput(time, InputType.TIME)) {
            throw new IllegalArgumentException("Invalid time string input ...");
        }
        String[] split = time.split(":");
        int[] timeArray = new int[] { Integer.parseInt(split[0]), Integer.parseInt(split[1]) };
        int hour = timeArray[0];
        int minute = timeArray[1];

        return date.atTime(hour, minute);
    }

    @FXML
    private void handleEventsButtonClicked() {
        String fxmlFileName = "AllEvents.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, AllEventsController.class, user, dataAccess);
        ControllerUtil.setSceneFromChild(loader, eventsButton);
    }

    @FXML
    private void handleLogOutButtonClicked() {
        String fxmlFileName = "LoginScreen.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(fxmlFileName, LoginController.class, null, dataAccess);
        ControllerUtil.setSceneFromChild(loader, logOutButton);   
    }
}
