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

import eventplanner.core.Event;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventCollectionJsonReaderTest {

    private static final File FILE = new File(EventCollectionJsonWriterTest.FILE_PATH);
    private Collection<Event> expectedEvents = new ArrayList<>();
    private Collection<Event> actualEvents = new ArrayList<>();

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
    void testLoad_dataIsUnchangedFromSave() {
        assertTrue(
                expectedEvents.stream()
                        .allMatch(expected -> {
                            return actualEvents.stream()
                                    .anyMatch(actual -> IOTestUtil.compareEventsUsersExcluded(actual, expected));
                        }));
    }

}
