package eventplanner.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * Test class for Event class in core module
 */
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class EventTest {

    private LocalDateTime localDateTime;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private List<User> users;
    private Event event1;
    private Event event2;
    private Event event3;

    @BeforeAll
    public void setup() {
        localDateTime = LocalDateTime.of(2022, 2, 22, 16, 30);
        user1 = new User("user1");
        user2 = new User("user2");
        user3 = new User("user3");
        user4 = new User("user4");
        users = Arrays.asList(user1, user2, user3, user4);
        event1 = new Event(EventType.CONCERT, "event1", localDateTime,
                localDateTime.plus(3, ChronoUnit.HOURS), "loc1", null);
        event2 = new Event(EventType.CONCERT, "event2", localDateTime,
                localDateTime.plus(2, ChronoUnit.HOURS), "loc2");
        event3 = new Event(EventType.CONCERT, "event3", localDateTime,
                localDateTime.plus(4, ChronoUnit.HOURS), "loc3", users);
    }
    
    @Test
    @Order(1)
    public void testConstructor_noUsersOnNullInput() {
        assertEquals(0, event1.getUsers().size());
        assertEquals(0, event2.getUsers().size());
        assertEquals(event1.getUsers(), event2.getUsers());
    }

    @Test
    @Order(2)
    public void testConstructor_throwsOnAnyNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new Event(null, null, null, null, null));
        assertThrows(IllegalArgumentException.class,
                () -> new Event(null, "name", localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), "loc"));
        assertThrows(IllegalArgumentException.class, () -> new Event(EventType.COURSE, null, localDateTime,
                localDateTime.plus(3, ChronoUnit.HOURS), "loc2"));
        assertThrows(IllegalArgumentException.class,
                () -> new Event(EventType.COURSE, "Hello", null, localDateTime, "location"));
        assertThrows(IllegalArgumentException.class,
                () -> new Event(EventType.COURSE, "Hello", localDateTime, null, "location1"));
        assertThrows(IllegalArgumentException.class, () -> new Event(EventType.COURSE, "Hello", localDateTime,
                localDateTime.plus(3, ChronoUnit.HOURS), null));
    }

    @Test
    @Order(3)
    public void testConstructor_throwsOnBlankNameOrLocation() {
        assertThrows(IllegalArgumentException.class,
                () -> new Event(EventType.COURSE, "", localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), "loc2"));
        assertThrows(IllegalArgumentException.class,
                () -> new Event(EventType.COURSE, " ", localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), "loc2"));
        assertThrows(IllegalArgumentException.class,
                () -> new Event(EventType.COURSE, "name1", localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), ""));
        assertThrows(IllegalArgumentException.class, () -> new Event(EventType.COURSE, "name2", localDateTime,
                localDateTime.plus(3, ChronoUnit.HOURS), " "));
    }

    @Test
    @Order(4)
    public void testGetters_getsValuesAsExpected() {
        assertEquals("event1", event1.getName());
        assertEquals("event2", event2.getName());
        assertEquals("event3", event3.getName());
        assertEquals("loc1", event1.getLocation());
        assertEquals("loc2", event2.getLocation());
        assertEquals("loc3", event3.getLocation());
        assertEquals(LocalDateTime.of(2022, 2, 22, 16, 30), event1.getStartDate());
        assertEquals(LocalDateTime.of(2022, 2, 22, 16, 30), event2.getStartDate());
        assertEquals(LocalDateTime.of(2022, 2, 22, 16, 30), event3.getStartDate());
        assertEquals(LocalDateTime.of(2022, 2, 22, 19, 30), event1.getEndDate());
        assertEquals(LocalDateTime.of(2022, 2, 22, 18, 30), event2.getEndDate());
        assertEquals(LocalDateTime.of(2022, 2, 22, 20, 30), event3.getEndDate());
        assertEquals(0, event1.getUsers().size());
        assertEquals(0, event2.getUsers().size());
        assertEquals(4, event3.getUsers().size());
    }

    @Test
    @Order(5)
    public void testAddUser_throwsIllegalArgumentExceptionOnNullInput() {
        assertThrows(IllegalArgumentException.class, () -> event1.addUser(null));
        assertThrows(IllegalArgumentException.class, () -> event2.addUser(null));
        assertThrows(IllegalArgumentException.class, () -> event3.addUser(null));
    }

    @Test
    @Order(6)
    public void testRemoveUser_throwsIllegalArgumentExceptionOnNullInput() {
        assertThrows(IllegalArgumentException.class, () -> event1.removeUser(null));
        assertThrows(IllegalArgumentException.class, () -> event2.removeUser(null));
        assertThrows(IllegalArgumentException.class, () -> event3.removeUser(null));
    }

    @Test
    @Order(7)
    public void testAddUser_addsAsExpected() {
        event1.addUser(user1);
        event1.addUser(user2);
        event2.addUser(user3);
        assertEquals(user1, event1.getUsers().get(0));
        assertEquals(4, event3.getUsers().size());
        assertEquals(1, event2.getUsers().size());
    }

    @Test
    @Order(8)
    public void testRemoveUser_removesAsExpected() {
        event3.removeUser(user1);
        assertEquals(3, event3.getUsers().size());
        event3.removeUser(user2);
        event3.removeUser(user3);
        assertEquals(1, event3.getUsers().size());
        event3.removeUser(user4);
        assertEquals(0, event3.getUsers().size());
    }
}