package eventplanner.core;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;



/**
 * Test class for Event class in core module
 */
public class EventTest {

    LocalDateTime localDateTime = LocalDateTime.of(2022, 2, 22, 16, 30);
    User user1 = new User("user1");
    User user2 = new User("user2");
    User user3 = new User("user3");
    User user4 = new User("user4");
    List<User> users = Arrays.asList(user1,user2,user3,user4); 
    Event event1 = new Event(EventType.CONCERT, "event1", localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), "loc1", null);
    Event event2 = new Event(EventType.CONCERT, "event2", localDateTime, localDateTime.plus(2, ChronoUnit.HOURS), "loc2");
    Event event3 = new Event(EventType.CONCERT, "event3", localDateTime, localDateTime.plus(4, ChronoUnit.HOURS), "loc3", users);


    @Test
    public void checkUserListIsEmptyWhenInstantiatedWithNullAsUserList_shouldBeEqual(){
        Assert.assertEquals(0, event1.getUsers().size());
        Assert.assertEquals(0, event2.getUsers().size());
        Assert.assertEquals(event1.getUsers(), event2.getUsers());
    }

    @Test
    public void validateEventOneOrAllIsNull_throwsIllegalArgumentException(){
        Assert.assertThrows(IllegalArgumentException.class, () -> new Event(null, null, null, null, null));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Event(null, "name", localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), "loc"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Event(EventType.COURSE, null, localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), "loc2"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Event(EventType.COURSE, "Hello", null, localDateTime, "location"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Event(EventType.COURSE, "Hello", localDateTime, null, "location1"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Event(EventType.COURSE, "Hello", localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), null));
    }

    @Test
    public void validateEventNameOrLocationIsBlank_throwsIllegalArgumentException(){
        Assert.assertThrows(IllegalArgumentException.class, () -> new Event(EventType.COURSE, "", localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), "loc2"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Event(EventType.COURSE, " ", localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), "loc2"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Event(EventType.COURSE, "name1", localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), ""));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Event(EventType.COURSE, "name2", localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), " "));
    }

    @Test
    public void eventsCreatedWithRightValues_shouldBeEqual(){
        Assert.assertEquals("event1", event1.getName());
        Assert.assertEquals("event2", event2.getName());
        Assert.assertEquals("event3", event3.getName());
        Assert.assertEquals("loc1", event1.getLocation());
        Assert.assertEquals("loc2", event2.getLocation());
        Assert.assertEquals("loc3", event3.getLocation());
        Assert.assertEquals(LocalDateTime.of(2022, 2, 22, 16, 30), event1.getStartDate());
        Assert.assertEquals(LocalDateTime.of(2022, 2, 22, 16, 30), event2.getStartDate());
        Assert.assertEquals(LocalDateTime.of(2022, 2, 22, 16, 30), event3.getStartDate());
        Assert.assertEquals(LocalDateTime.of(2022, 2, 22, 19, 30), event1.getEndDate());
        Assert.assertEquals(LocalDateTime.of(2022, 2, 22, 18, 30), event2.getEndDate());
        Assert.assertEquals(LocalDateTime.of(2022, 2, 22, 20, 30), event3.getEndDate());
        Assert.assertEquals(0, event1.getUsers().size());
        Assert.assertEquals(0, event2.getUsers().size());
        Assert.assertEquals(4, event3.getUsers().size());
    }
    
    @Test
    public void testAddUser_throwsIllegalArgumentException(){
        Assert.assertThrows(IllegalArgumentException.class, () -> event1.addUser(null));
        Assert.assertThrows(IllegalArgumentException.class, () -> event2.addUser(null));
        Assert.assertThrows(IllegalArgumentException.class, () -> event3.addUser(null));
    }

    @Test
    public void testRemoveUser_throwsIllegalArgumentException(){
        Assert.assertThrows(IllegalArgumentException.class, () -> event1.removeUser(null));
        Assert.assertThrows(IllegalArgumentException.class, () -> event2.removeUser(null));
        Assert.assertThrows(IllegalArgumentException.class, () -> event3.removeUser(null));
    }

    @Test
    public void testAddUser_ok(){
        event1.addUser(user1);
        event1.addUser(user2);
        event2.addUser(user3);
        Assert.assertEquals(user1, event1.getUsers().get(0));
        Assert.assertEquals(4, event3.getUsers().size());
        Assert.assertEquals(1, event2.getUsers().size());
    }

    @Test
    public void testRemoveUser_ok(){
        event3.removeUser(user1);
        Assert.assertEquals(3, event3.getUsers().size());
        event3.removeUser(user2);
        event3.removeUser(user3);
        Assert.assertEquals(1, event3.getUsers().size());
        event3.removeUser(user4);
        Assert.assertEquals(0, event3.getUsers().size());
    }
}