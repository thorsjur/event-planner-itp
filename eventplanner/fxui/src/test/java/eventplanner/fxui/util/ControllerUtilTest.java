package eventplanner.fxui.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.BooleanSupplier;

import org.junit.jupiter.api.Test;

import eventplanner.core.Event;
import eventplanner.core.EventType;
import eventplanner.core.User;
import eventplanner.fxui.AllEventsController;
import eventplanner.fxui.App;
import eventplanner.fxui.DataAccess;
import eventplanner.fxui.EventCell;
import eventplanner.fxui.LocalDataAccess;
import eventplanner.fxui.NewEventController;
import javafx.fxml.FXMLLoader;

class ControllerUtilTest {

    private boolean flag = false;
    private final User user = new User("test@test.test", "testox", true);
    private static final String EXAMPLE_PATH = "example/path/";
    private final DataAccess dataAccess = new LocalDataAccess();

    @Test
    void testGetValidationFocusListener() {
        BooleanSupplier validation = () -> true;
        Runnable ifValid = () -> flag = true;
        Runnable ifInvalid = () -> flag = false;

        // Testing when focus on field is lost
        ControllerUtil.getValidationFocusListener(validation, ifValid, ifInvalid).changed(null, true, false);
        assertTrue(flag);

        validation = () -> false;
        ControllerUtil.getValidationFocusListener(validation, ifValid, ifInvalid).changed(null, true, false);
        assertFalse(flag);

        // Assert that flag does not change when field is focused
        validation = () -> true;
        ControllerUtil.getValidationFocusListener(validation, ifValid, ifInvalid).changed(null, false, true);
        assertFalse(flag);
    }

    @Test
    void testGetFXMLLoaderWithFactory_throwsExceptionOnUnknownClass() {
        assertThrows(IllegalArgumentException.class,
                () -> ControllerUtil.getFXMLLoaderWithFactory(null, null, null, dataAccess));
        assertThrows(IllegalArgumentException.class,
                () -> ControllerUtil.getFXMLLoaderWithFactory(EXAMPLE_PATH, App.class, user, dataAccess));
        assertThrows(IllegalArgumentException.class,
                () -> ControllerUtil.getFXMLLoaderWithFactory(null, EventCell.class, null, dataAccess));
    }

    @Test
    void testGetFXMLLoaderWithFactory_doesNotThrowExceptionOnKnownClass() {
        assertDoesNotThrow(() -> ControllerUtil.getFXMLLoaderWithFactory(
                        "AllEvents.fxml",
                        AllEventsController.class,
                        user,
                        dataAccess
                )
        );

        assertDoesNotThrow(() -> ControllerUtil.getFXMLLoaderWithFactory(
                        "CreateEvent.fxml",
                        NewEventController.class,
                        user,
                        dataAccess
                )
        );
    }

    @Test
    void testGetFXMLLoaderWithFactory_hasControllerFactory() {
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(EXAMPLE_PATH, AllEventsController.class, user,
                dataAccess);

        assertNotNull(loader.getControllerFactory());
    }

    @Test
    void testGetDateComparator_areEqual() {
        LocalDateTime date = LocalDateTime.of(2022, 8, 13, 13, 12);
        LocalDateTime date2 = LocalDateTime.of(2022, 8, 13, 13, 12);
        Event event = createTestEvent(date);
        Event event2 = createTestEvent(date2);
        assertEquals(0, ControllerUtil.getDateComparator().compare(event, event2));
    }

    @Test
    void testGetDateComparator_isBefore() {
        LocalDateTime date = LocalDateTime.of(2022, 1, 13, 13, 2);
        LocalDateTime date2 = LocalDateTime.of(2023, 8, 13, 12, 12);
        Event event = createTestEvent(date);
        Event event2 = createTestEvent(date2);
        assertEquals(-1, ControllerUtil.getDateComparator().compare(event, event2));
    }

    @Test
    void testGetDateComparator_isAfter() {
        LocalDateTime date = LocalDateTime.of(2024, 4, 12, 13, 12);
        LocalDateTime date2 = LocalDateTime.of(2023, 8, 13, 13, 12);
        Event event = createTestEvent(date);
        Event event2 = createTestEvent(date2);
        assertEquals(1, ControllerUtil.getDateComparator().compare(event, event2));
    }

    private static Event createTestEvent(LocalDateTime dateStart) {
        return new Event(null,
                EventType.COURSE,
                "name",
                dateStart,
                dateStart.plus(100,
                ChronoUnit.MINUTES),
                "location"
        );
    }

}
