package EventPlanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import EventPlanner.Event;
import EventPlanner.EventType;
import EventPlanner.User;

/**
 * Unit test for simple App.
 */
public class EventTest 
{
    public Event ev1;
    public LocalDateTime date1 = LocalDateTime.now();
    public String eventName = "Testevent😅";
    public String location = "LaBamba";
    public EventType eventType = EventType.CONCERT;

    @BeforeEach
    public void init() {
        ev1 = new Event(eventType, eventName, date1, date1, location);
    }

    @Test
    public void testGetters()
    {
        assertEquals(date1, ev1.getStartDate());
        assertEquals(date1, ev1.getEndDate());
        assertEquals(eventType, ev1.getType());
        assertEquals(eventName, ev1.getName());
        assertEquals(location, ev1.getLocation());

    }

    @Test
    public void testUserImplementation()
    {
        User us1 = new User("Testuser");
        ev1.addUser(us1);
        assertEquals(ev1.getUsers().get(0), us1);
        ev1.removeUser(us1);
        assertTrue(ev1.getUsers().isEmpty());

    }
}