package eventplanner.json.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.json.EventCollectionJsonReader;
import eventplanner.json.EventCollectionJsonWriter;
import eventplanner.json.IOTestUtil;
import eventplanner.json.UserCollectionJsonReader;
import eventplanner.json.UserCollectionJsonWriter;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IOUtilTest {

    public static final String EVENT_FILE_PATH = "src/test/java/resources/data/event.json";
    private static final File EVENT_FILE = new File(EVENT_FILE_PATH);
    public static final String USER_FILE_PATH = "src/test/java/resources/data/user.json";
    private static final File USER_FILE = new File(USER_FILE_PATH);
    

    // Gets read before every test, assuming EventCollectionJsonReader and UserCollectionReader work as
    // expected
    private final Collection<Event> events = new ArrayList<>();
    private final Collection<User> users = new ArrayList<>();
    
    private final Event event = IOTestUtil.getUniqueEvent();
    private final User user = IOTestUtil.getUniqueUser();

    private EventCollectionJsonReader eventReader = new EventCollectionJsonReader();
    private UserCollectionJsonReader userReader = new UserCollectionJsonReader();


    @BeforeAll
    public void setup() {
        events.addAll(IOTestUtil.getPseudoRandomEvents(15));
        users.addAll(IOTestUtil.getPseudoRandomUsers(15));
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        EventCollectionJsonWriter eventWriter = new EventCollectionJsonWriter();
        eventWriter.save(events, EVENT_FILE);

        UserCollectionJsonWriter userWriter = new UserCollectionJsonWriter();
        userWriter.save(users, USER_FILE);
    }

    @AfterEach
    public void tearDown() throws IOException {
        EventCollectionJsonWriter eventWriter = new EventCollectionJsonWriter();
        eventWriter.save(new ArrayList<Event>(), EVENT_FILE);
        
        UserCollectionJsonWriter userWriter = new UserCollectionJsonWriter();
        userWriter.save(new ArrayList<User>(), USER_FILE);
    }


    @Test
    public void testAppendEventsToFile() throws IOException {
        Collection<Event> expectedEvents = new ArrayList<>();         
        expectedEvents.addAll(IOTestUtil.getPseudoRandomEvents(5));

        IOUtil.appendEventsToFile(expectedEvents, EVENT_FILE);
        
        Collection<Event> actualEvents = eventReader.load(EVENT_FILE);
        
        assertTrue(expectedEvents.stream()
                    .allMatch(expected -> actualEvents.contains(expected)));
    }

    @Test
    public void testAppendEventToFile() throws IOException {
        IOUtil.appendEventToFile(event, EVENT_FILE);
        Collection<Event> actualEvents = eventReader.load(EVENT_FILE);
        
        assertTrue(actualEvents.contains(event));
    }
    
    @Test
    public void testAppendUsersToFile() throws IOException {
        Collection<User> expectedUsers = new ArrayList<>();         
        expectedUsers.addAll(IOTestUtil.getPseudoRandomUsers(5));

        IOUtil.appendUsersToFile(expectedUsers, USER_FILE);
        
        Collection<User> actualUsers = userReader.load(USER_FILE);
        
        assertTrue(expectedUsers.stream()
                    .allMatch(expected -> actualUsers.contains(expected)));
    }

    @Test
    public void testAppendUserToFile() throws IOException {
        IOUtil.appendUserToFile(user, USER_FILE);
        Collection<User> actualUsers = userReader.load(USER_FILE);

        assertTrue(actualUsers.contains(user));
    }

    @Test
    public void testHasFileExtension() {
        assertTrue(IOUtil.hasFileExtension(EVENT_FILE, "event.json"));
        assertFalse(IOUtil.hasFileExtension(USER_FILE, "event.json"));
    }

    @Test
    public void testLoadUsersMatchingEmail() throws IOException {

        List<String> emails = new ArrayList<>();
        Iterator<User> usersIterator = users.iterator();
        
        // Adding all emails from our users collection to a list
        while (usersIterator.hasNext()) {
            emails.add(usersIterator.next().email());
        }
            
        // Retrieving the users matching these emails from the user.json test file
        Collection<User> matchingUsers = IOUtil.loadUsersMatchingEmail(emails, USER_FILE);

        assertTrue(users.stream()
                    .allMatch(expected -> matchingUsers.contains(expected)));
        
        // Should not find such a user
        String email = "" + System.currentTimeMillis() + "@example.com";
        assertNull(IOUtil.loadUserMatchingEmail(email, USER_FILE));

        // Should find such a user
        assertNotNull(IOUtil.loadUserMatchingEmail(emails.get(0), USER_FILE));
    }

    @Test
    public void testLoadEventsMatchingId() throws IOException {

        List<String> idList = new ArrayList<>();
        Iterator<Event> eventIterator = events.iterator();
        
        // Adding all ids from our event collection to a list
        while (eventIterator.hasNext()) {
            idList.add(eventIterator.next().getId().toString());
        }
            
        // Retrieving the events matching these ids from the event.json test file
        Collection<Event> matchingEvents = IOUtil.loadEventsMatchingId(idList, EVENT_FILE);

        assertTrue(events.stream()
                    .allMatch(expected -> matchingEvents.contains(expected)));
        
        // Should not find such an event
        String emptyId = "";
        assertNull(IOUtil.loadEventMatchingId(emptyId, EVENT_FILE));

        // Should find such an event
        assertNotNull(IOUtil.loadEventMatchingId(idList.get(0), EVENT_FILE));
    }

    @Test
    public void testLoadAllUsers() throws IOException {
        Collection<User> actualUsers = IOUtil.loadAllUsers(USER_FILE);

        assertTrue(users.stream()
                    .allMatch(expected -> actualUsers.contains(expected)));  
    }

    @Test
    public void testOverwriteUsers() throws IOException {
        List<User> expectedUsers = new ArrayList<>();         
        expectedUsers.addAll(IOTestUtil.getPseudoRandomUsers(5));

        IOUtil.overwriteUsers(expectedUsers, USER_FILE);

        Collection<User> actualUsers = userReader.load(USER_FILE);

        // Initial 15 random users got overwritten by our new 5 random users
        assertTrue(actualUsers.stream()
                    .allMatch(u -> expectedUsers.contains(u)));      
    }

    @Test
    public void testLoadAllEvents() throws IOException {
        Collection<Event> actuaEvents = IOUtil.loadAllEvents(EVENT_FILE);

        assertTrue(events.stream()
                    .allMatch(expected -> actuaEvents.contains(expected)));  
    }

    @Test
    public void testOverwriteEvents() throws IOException {
        List<Event> expectedEvents = new ArrayList<>();         
        expectedEvents.addAll(IOTestUtil.getPseudoRandomEvents(5));

        IOUtil.overwriteEvents(expectedEvents, EVENT_FILE);

        Collection<Event> actualEvnets = eventReader.load(EVENT_FILE);

        // Initial 15 random events got overwritten by our new 5 random events
        assertTrue(actualEvnets.stream()
                    .allMatch(u -> expectedEvents.contains(u)));      
    }

    @Test
    public void testAddUserToEvents() throws IOException {
        IOUtil.addUserToEvents(events, user, EVENT_FILE);
        Collection<Event> alteredEvents = eventReader.load(EVENT_FILE);

        for (Event evt : alteredEvents) {
            Collection<User> usr = evt.getUsers();
            assertTrue(usr.stream()
                .anyMatch(u -> u.email().equals(user.email())));    
        }
    }

    @Test
    public void testRemoveUserFromEvent() throws IOException {
        Iterator<Event> eventIterator = events.iterator();
        final Event evt = eventIterator.next();
        final User usr = evt.getUsers().get(0);

        // User is associated to event
        assertTrue(evt.getUsers().stream()
                    .anyMatch(u -> u.equals(usr)));

        IOUtil.removeUserFromEvent(evt, usr, EVENT_FILE);
        
        Collection<Event> alteredEvents = eventReader.load(EVENT_FILE);
        
        Event alteredEvt = alteredEvents.stream().filter(e -> e.equals(evt)).findAny().get();

        Collection<User> userCollection = alteredEvt.getUsers();

        // User is removed
        assertFalse(userCollection.stream()
                    .anyMatch(u -> u.equals(usr)));      
    }

    @Test
    public void testDeleteEventFromFile() throws IOException {
        EventCollectionJsonWriter writer = new EventCollectionJsonWriter();
        writer.save(event, EVENT_FILE);
        
        IOUtil.deleteEventFromFile(event, EVENT_FILE);
        
        Collection<Event> events = eventReader.load(EVENT_FILE);

        assertFalse(events.stream()
                .anyMatch(e -> e.equals(event)));  

        writer.save(event, EVENT_FILE);
        IOUtil.deleteEventFromFile(event.getId().toString(), EVENT_FILE);

        assertFalse(events.stream()
                .anyMatch(e -> e.equals(event)));  
    }

    @Test
    public void testDeleteUserFromFile() throws IOException {
        UserCollectionJsonWriter writer = new UserCollectionJsonWriter();
        writer.save(user, USER_FILE);

        IOUtil.deleteUserFromFile(user.email(), USER_FILE);
        
        Collection<User> users = userReader.load(USER_FILE);

        assertFalse(users.stream()
                .anyMatch(u -> u.equals(user)));  
    }

}
