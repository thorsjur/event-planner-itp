package eventplanner.rest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import eventplanner.core.Event;
import eventplanner.core.EventType;
import eventplanner.core.User;
import eventplanner.json.CustomObjectMapper;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { EventController.class, RestServiceApplication.class })
@WebMvcTest
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String SERVICE_PATH = "http://localhost:8080/";
    private static final CustomObjectMapper MAPPER = new CustomObjectMapper();

    private List<UUID> tempEventUuids = new ArrayList<>();

    @BeforeEach
    public void beforeEach() throws Exception {
        Event event = getTestEvent("TEST_NAME", "TEST_LOCATION");
        Event event2 = getTestEvent("TEST_NAME2", "TEST_LOCATION2");
        addTestEvent(event);
        addTestEvent(event2);
        tempEventUuids.add(event.getId());
        tempEventUuids.add(event2.getId());
    }

    @AfterEach
    public void afterEach() throws Exception {
        for (UUID uuid : tempEventUuids) {
            removeTestEvent(uuid);
        }
        tempEventUuids.clear();
    }

    @Test
    public void testAll_retrievesEventsAsExpected() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(SERVICE_PATH + "event/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        Collection<Event> events = MAPPER.readValue(response, new TypeReference<Collection<Event>>() {});

        assertTrue(events.size() >= 2);
        assertTrue(events.stream()
                .anyMatch(event -> event.getName().equals("TEST_NAME")));
        assertTrue(events.stream()
                .anyMatch(event -> event.getName().equals("TEST_NAME2")));
    }

    @Test
    public void testEvent_retrievesEventAsExpected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(SERVICE_PATH + "event?id=" + tempEventUuids.get(0).toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void testUpdate() throws Exception {
        UUID uuid = tempEventUuids.get(0);
        Event expectedEvent = loadEvent(uuid);
        User expectedUser = new User("test@user.com", "passw", true);
        expectedEvent.addUser(expectedUser);
        mockMvc.perform(MockMvcRequestBuilders.put(SERVICE_PATH + "event/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(expectedEvent))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Event actualEvent = loadEvent(uuid);
        assertTrue(actualEvent.getUsers().get(0).email().equals(expectedUser.email()));
    }

    @Test
    public void testCreate() throws Exception {
        Event event = getTestEvent("TEST_NAME3", "TEST_LOCATION3");
        tempEventUuids.add(event.getId());

        mockMvc.perform(MockMvcRequestBuilders.post(SERVICE_PATH + "event/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(event))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertDoesNotThrow(() -> loadEvent(event.getId()));
    }

    @Test
    public void testDelete() throws Exception {
        Event event = getTestEvent("TEST_NAME3", "TEST_LOCATION3");
        addTestEvent(event);

        mockMvc.perform(MockMvcRequestBuilders.delete(SERVICE_PATH + "event/" + event.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThrows(MismatchedInputException.class, () -> loadEvent(event.getId()));
    }



    private Event getTestEvent(String name, String location) {
        return new Event(null,
                EventType.QUIZ,
                name,
                LocalDateTime.now(),
                LocalDateTime.now().plus(10, ChronoUnit.HOURS),
                location);
    }

    private void addTestEvent(Event event) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(SERVICE_PATH + "event/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(event))
                .accept(MediaType.APPLICATION_JSON));
    }

    private void removeTestEvent(UUID uuid) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(SERVICE_PATH + "event/" + uuid.toString())
                .accept(MediaType.APPLICATION_JSON));
    }

    private Event loadEvent(UUID uuid) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(SERVICE_PATH + "event?id=" + uuid.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = result.getResponse().getContentAsString();
        return getEvent(response);
    }

    private Event getEvent(String content) throws Exception{
        return MAPPER.readValue(content, Event.class);
    }
}
