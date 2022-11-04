package eventplanner.integrationtests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import eventplanner.core.Event;
import eventplanner.core.EventType;
import eventplanner.core.User;
import eventplanner.json.CustomObjectMapper;
import eventplanner.rest.RestServiceApplication;

@SpringBootTest(classes = RestServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
class IntegrationTests {

    @LocalServerPort
    private int port;

    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private CustomObjectMapper mapper = new CustomObjectMapper();

    private List<UUID> tempEventUuids = new ArrayList<>();
    private List<String> tempUserEmails = new ArrayList<>();

    @BeforeEach
    public void beforeEach() throws Exception {
        Event event = getTestEvent("TEST_NAME", "TEST_LOCATION");
        Event event2 = getTestEvent("TEST_NAME2", "TEST_LOCATION2");
        addTestEvent(event, true);
        addTestEvent(event2, true);
        User user = getTestUser("TEST@EMAIL.com", "TEST_LOCATION");
        addTestUser(user, true);
    }

    @AfterEach
    public void afterEach() throws Exception {
        for (UUID uuid : tempEventUuids) {
            removeTestEvent(uuid);
        }
        for (String email : tempUserEmails) {
            removeTestUser(email);
        }
        tempUserEmails.clear();
        tempEventUuids.clear();
        headers.clear();
    }

    @Test
    public void testCreateEvent() throws JsonProcessingException {
        headers.setContentType(MediaType.APPLICATION_JSON);

        Event expectedEvent = getTestEvent("TEST_NAME3", "TEST_LOCATION3");

        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(expectedEvent), headers);

        ResponseEntity<String> response = testRestTemplate.exchange(
                getURL("/event/create"),
                HttpMethod.POST, entity, String.class);

        if ((response.getStatusCode() == HttpStatus.OK) && response.getBody().equals("true")) {
            tempEventUuids.add(expectedEvent.getId());
        } else {
            fail("Event not created as expected: " + response.getStatusCode());
        }

        Event actualEvent = loadEvent(expectedEvent.getId());
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void testGetsAllEvents() throws JsonProcessingException {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = testRestTemplate.exchange(
                getURL("/event/all"),
                HttpMethod.GET, entity, String.class);

        Collection<Event> events = mapper.readValue(response.getBody(), new TypeReference<Collection<Event>>() {
        });
        assertTrue(events.size() >= 2);
        assertTrue(events.stream()
                .anyMatch(event -> event.getId().equals(tempEventUuids.get(0))));
        assertTrue(events.stream()
                .anyMatch(event -> event.getId().equals(tempEventUuids.get(1))));
    }

    @Test
    public void testGetEvent() throws JsonProcessingException {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        Event expectedEvent = getTestEvent("TEST_NAME3", "TEST_LOCATION3");
        addTestEvent(expectedEvent, true);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = testRestTemplate.exchange(
                getURL("/event?id=" + expectedEvent.getId().toString()),
                HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Event actualEvent = readEvent(response.getBody());
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void testDeleteEvent() throws JsonProcessingException {
        Event eventToBeDeleted = getTestEvent("TEST_NAME3", "TEST_LOCATION3");
        addTestEvent(eventToBeDeleted, false);

        // Assert the event has been added
        assertDoesNotThrow(() -> loadEvent(eventToBeDeleted.getId()));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                getURL("/event/" + eventToBeDeleted.getId().toString()),
                HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThrows(Exception.class, () -> loadEvent(eventToBeDeleted.getId()));
    }

    @Test
    public void testUpdateEvent() throws JsonProcessingException {
        UUID uuid = tempEventUuids.get(0);
        Event expectedEvent = loadEvent(uuid);
        User expectedUser = new User("test@user.com", "passw", true);
        expectedEvent.addUser(expectedUser);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        // Asserts the event doesn't have the user already
        assertFalse(loadEvent(uuid).getUsers().stream().anyMatch(user -> user.email().equals(expectedUser.email())));

        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(expectedEvent), headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                getURL("/event/update"),
                HttpMethod.PUT, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertTrue(loadEvent(uuid).getUsers().stream().anyMatch(user -> user.email().equals(expectedUser.email())));
    }

    @Test
    public void testGetUser() throws JsonProcessingException {
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String email = tempUserEmails.get(0);
        ResponseEntity<String> response = testRestTemplate.exchange(
                getURL("/user/get?email=" + email),
                HttpMethod.GET, entity, String.class);

        assertTrue(HttpStatus.OK == response.getStatusCode());
        assertTrue(email.equals(readUser(response.getBody()).email()));
    }

    @Test
    public void testCreateUser() throws JsonProcessingException {
        User expectedUser = new User("TEST2@EXAMPLE.com", "PASSWORD", true);
        tempUserEmails.add(expectedUser.email());

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(expectedUser), headers);

        ResponseEntity<String> response = testRestTemplate.exchange(
                getURL("/user/create"),
                HttpMethod.POST, entity, String.class);

        assertTrue(HttpStatus.OK == response.getStatusCode());

        User actualUser = loadUser(expectedUser.email());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testDeleteUser() throws JsonProcessingException {
        User user = new User("TEST2@EXAMPLE.com", "PASSWORD", true);
        addTestUser(user, false);

        // Assert the user was created
        assertDoesNotThrow(() -> loadUser(user.email()));

        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                getURL("/user/" + user.email()),
                HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Should throw exception if no user found -> the user was deleted
        assertThrows(Exception.class, () -> loadUser(user.email()));
    }

    private String getURL(String uri) {
        return "http://localhost:" + port + uri;
    }

    private Event getTestEvent(String name, String location) {
        return new Event(null,
                EventType.QUIZ,
                name,
                LocalDateTime.now(),
                LocalDateTime.now().plus(10, ChronoUnit.HOURS),
                location);
    }

    private void addTestEvent(Event event, boolean autoDelete) throws JsonProcessingException {
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (autoDelete) {
            tempEventUuids.add(event.getId());
        }

        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(event), headers);

        testRestTemplate.exchange(
                getURL("/event/create"),
                HttpMethod.POST, entity, String.class);
    }

    private void removeTestEvent(UUID uuid) throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(headers);

        testRestTemplate.exchange(
                getURL("/event/" + uuid.toString()),
                HttpMethod.DELETE, entity, String.class);
    }

    private Event loadEvent(UUID uuid) throws JsonProcessingException {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = testRestTemplate.exchange(
                getURL("/event?id=" + uuid.toString()),
                HttpMethod.GET, entity, String.class);
        return readEvent(response.getBody());
    }

    private Event readEvent(String content) throws JsonProcessingException {
        return mapper.readValue(content, Event.class);
    }

    private User getTestUser(String name, String password) {
        return new User(name, password, true);
    }

    private void addTestUser(User user, boolean autoDelete) throws JsonProcessingException {
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (autoDelete) {
            tempUserEmails.add(user.email());
        }

        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(user), headers);

        testRestTemplate.exchange(
                getURL("/user/create"),
                HttpMethod.POST, entity, String.class);
    }

    private void removeTestUser(String email) throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(headers);

        testRestTemplate.exchange(
                getURL("/user/" + email),
                HttpMethod.DELETE, entity, String.class);
    }

    private User loadUser(String email) throws JsonProcessingException {
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                getURL("/user/get?email=" + email),
                HttpMethod.GET, entity, String.class);

        return readUser(response.getBody());
    }

    private User readUser(String content) throws JsonProcessingException {
        return mapper.readValue(content, User.class);
    }

}