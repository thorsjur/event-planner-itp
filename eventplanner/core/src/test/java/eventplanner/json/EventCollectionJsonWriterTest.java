package eventplanner.json;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import eventplanner.core.Event;

@TestInstance(Lifecycle.PER_CLASS)
class EventCollectionJsonWriterTest {

    static final String FILE_PATH = "src/test/java/resources/data/event.json";
    private static final File FILE = new File(FILE_PATH);

    private Collection<Event> actualEvents;

    // Gets read before every test, assuming EventCollectionJsonReader works as
    // expected
    private final Collection<Event> expectedEvents = new ArrayList<>();

    @BeforeAll
    void setup() throws IOException {
        EventCollectionJsonWriter writer = new EventCollectionJsonWriter();
        expectedEvents.addAll(IOTestUtil.getPseudoRandomEvents(15));
        writer.save(expectedEvents, FILE);
    }

    @AfterAll
    void tearDown() throws IOException {
        EventCollectionJsonWriter writer = new EventCollectionJsonWriter();
        writer.save(new ArrayList<Event>(), FILE);
    }

    @BeforeEach
    void beforeEach() throws IOException {
        EventCollectionJsonReader reader = new EventCollectionJsonReader();
        actualEvents = reader.load(FILE);
    }

    @Test
    void testSave_retainsTheEventData() {
        assertTrue(
                expectedEvents.stream()
                        .allMatch(expected -> {
                            return actualEvents.stream()
                                    .anyMatch(actual -> IOTestUtil.compareEventsUsersExcluded(actual, expected));
                        }));
    }

}
