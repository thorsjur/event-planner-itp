package eventplanner.fxui.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import eventplanner.core.Event;
import eventplanner.core.EventType;
import eventplanner.core.User;
import eventplanner.fxui.App;
import eventplanner.fxui.AppController;
import eventplanner.fxui.EventCell;
import eventplanner.fxui.MyEventsController;
import eventplanner.fxui.NewEventController;
import javafx.fxml.FXMLLoader;

public class ControllerUtilTest {

    private boolean flag = false;
    private final User user = new User("email", "password", true); //TODO - sjekk om dette ble korrekt
    private final String path = "path";
    
    @Test
    public void testGetValidationFocusListener() {
        Supplier<Boolean> validation = () -> true;
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
    public void testGetFXMLLoaderWithFactory_throwsExceptionOnUnknownClass() {
        assertThrows(IllegalArgumentException.class, () -> ControllerUtil.getFXMLLoaderWithFactory(null, null, null));
        assertThrows(IllegalArgumentException.class, () -> ControllerUtil.getFXMLLoaderWithFactory(path, App.class, user));
        assertThrows(IllegalArgumentException.class, () -> ControllerUtil.getFXMLLoaderWithFactory(null, EventCell.class, null));
    }

    @Test
    public void testGetFXMLLoaderWithFactory_doesNotThrowExceptionOnKnownClass() {
        assertDoesNotThrow(() -> ControllerUtil.getFXMLLoaderWithFactory(path, AppController.class, null));
        assertDoesNotThrow(() -> ControllerUtil.getFXMLLoaderWithFactory(path, MyEventsController.class, user));
        assertDoesNotThrow(() -> ControllerUtil.getFXMLLoaderWithFactory(path, NewEventController.class, null));
    }

    @Test
    public void testGetFXMLLoaderWithFactory_hasControllerFactory() {
        FXMLLoader loader = ControllerUtil.getFXMLLoaderWithFactory(path, AppController.class, user);
        assertNotNull(loader.getControllerFactory());
    }

    @Test
    public void testGetRandomFiveDigitString_isOfLengthFive() {
        for (int i = 0; i < 30; i++) {
            String string = ControllerUtil.getRandomFiveDigitString();
            assertEquals(5, string.length());
        }
    }

    @Test
    public void testGetRandomFiveDigitString_isOnlyDigits() {
        for (int i = 0; i < 30; i++) {
            String string = ControllerUtil.getRandomFiveDigitString();
            assertTrue(string.matches("^\\d+$"));
        }
    }

    @Test
    public void testGetDateComparator_areEqual() {
        LocalDateTime date = LocalDateTime.of(2022, 8, 13, 13, 12);
        LocalDateTime date2 = LocalDateTime.of(2022, 8, 13, 13, 12);
        Event event = createEvent(date);
        Event event2 = createEvent(date2);
        assertEquals(0, ControllerUtil.getDateComparator().compare(event, event2));
    }

    @Test
    public void testGetDateComparator_isBefore() {
        LocalDateTime date = LocalDateTime.of(2022, 1, 13, 13, 2);
        LocalDateTime date2 = LocalDateTime.of(2023, 8, 13, 12, 12);
        Event event = createEvent(date);
        Event event2 = createEvent(date2);
        assertEquals(-1, ControllerUtil.getDateComparator().compare(event, event2));
    }

    @Test
    public void testGetDateComparator_isAfter() {
        LocalDateTime date = LocalDateTime.of(2024, 4, 12, 13, 12);
        LocalDateTime date2 = LocalDateTime.of(2023, 8, 13, 13, 12);
        Event event = createEvent(date);
        Event event2 = createEvent(date2);
        assertEquals(1, ControllerUtil.getDateComparator().compare(event, event2));
    }

    private static Event createEvent(LocalDateTime dateStart) {
        return new Event(
            EventType.COURSE,
            "name",
            dateStart,
            dateStart.plus(100, ChronoUnit.MINUTES),
            "location"
        );
    }


}
