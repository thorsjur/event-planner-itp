package eventplanner.fxui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import eventplanner.core.User;
import eventplanner.core.Event;
import eventplanner.core.EventType;
import eventplanner.fxui.util.ControllerUtil;
import eventplanner.fxui.util.InputType;
import eventplanner.fxui.util.Validation;
import eventplanner.json.util.IOUtil;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewEventController {

    @FXML
    private Button eventsButton, myEventsButton;

    @FXML
    private DatePicker startDatePicker, endDatePicker;

    @FXML
    private TextField startTimeField, endTimeField, nameField, descField, locationField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextArea outputMessage;

    private User user;

    @FXML
    private void initialize() {
        Stream.of(EventType.values())
            .map(et -> et.toString())
            .forEach(typeComboBox.getItems()::add);

        // Adds listeners to all input fields that signals input-validity to the user.
        addValidationFocusListener(startTimeField, InputType.TIME);
        addValidationFocusListener(endTimeField, InputType.TIME);
        addValidationFocusListener(nameField, InputType.NAME);
        addValidationFocusListener(locationField, InputType.LOCATION);
        addValidationFocusListener(startDatePicker, InputType.DATE);
        addValidationFocusListener(endDatePicker, InputType.DATE);
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

        LocalDateTime localDateTimeStart = getLocalDateTimeObject(startTime, startDate);
        LocalDateTime localDateTimeEnd = getLocalDateTimeObject(endTime, endDate);
        Event event = new Event(eventType, name, localDateTimeStart, localDateTimeEnd, location, new ArrayList<>());

        boolean saveFlag = true;
        try {
            IOUtil.appendEventToFile(event, null);
        } catch (IOException e) {
            System.out.println("Something went wrong while saving\nCan't add event to file.");
            saveFlag = false;
        }

        resetFields();
        String outputMessageString = saveFlag ? "New event created successfully" : "Error while saving event ...";
        outputMessage.setText(outputMessageString);
    }

    private void displayErrorMessages(ArrayList<Validation.ErrorType> errors) {
        StringBuilder sb = new StringBuilder();
        errors.forEach(e -> {
            sb.append(e.err_message + "\n");
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

    /**
     * NOTE: Assumes validated input
     * 
     * @param   startTime
     * @return  An array of length 2 containing hour and minutes
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

    @FXML
    private void handleMyEventsButtonClicked() {
        String pathName = "MyEvents.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoader(pathName);
        ControllerUtil.setSceneFromChild(loader, myEventsButton);
        MyEventsController myEventsController = loader.getController();
        myEventsController.setUser(getUser());
    }

    @FXML
    private void handleEventsButtonClicked(){
        String pathName = "AllEvents.fxml";
        FXMLLoader loader = ControllerUtil.getFXMLLoader(pathName);
        ControllerUtil.setSceneFromChild(loader, myEventsButton);
        AppController appController = loader.getController();
        appController.setUser(getUser());
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User getUser() {
        return this.user;
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

    /**
     * @param   control input control
     * @param   type    specified type of input
     * @return          ChangeListener that signals validity of input
     */
    private ChangeListener<Boolean> getValidationListener(Control control, InputType type) {
        validateArguments(control, type);

        switch (type) { 
            case DATE:
                DatePicker datePicker = (DatePicker) control;
                return ControllerUtil.getValidationFocusListener(
                        () -> {
                            return Validation.isValidDateInput(datePicker.getValue());
                        },
                        () -> handleValidDatePicker(datePicker),
                        () -> handleInvalidDatePicker(datePicker));
            case EVENT_TYPE:
                return null;
            default: // All text inputs are handled by the default case
                return getTextFieldValidationListener((TextField) control, type);
        }
    }

    private ChangeListener<Boolean> getTextFieldValidationListener(TextField field, InputType type) {
        return ControllerUtil.getValidationFocusListener(
                () -> {
                    return Validation.isValidTextInput(field.getText(), type);
                },
                () -> handleValidTextField(field),
                () -> handleInvalidTextField(field));
    }

    private void addValidationFocusListener(Control control, InputType Type) {
        control.focusedProperty().addListener(getValidationListener(control, Type));
    }

    /**
     * Asserts that the given arguments are compatible
     * @param control
     * @param type
     * @throws IllegalArgumentException if fields are not compatible
     */
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
