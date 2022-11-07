package eventplanner.json;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import eventplanner.core.Event;

@TestInstance(Lifecycle.PER_CLASS)
class EventCollectionJsonWriterTest {

    static final String FILE_PATH = "src/test/java/resources/data/event.json";
    private static final File FILE = new File(FILE_PATH);
    private static final EventCollectionJsonWriter WRITER = new EventCollectionJsonWriter();
    private static final EventCollectionJsonReader READER = new EventCollectionJsonReader();

    private Collection<Event> actualEvents;

    // Gets read before every test, assuming EventCollectionJsonReader works as
    // expected
    private final Collection<Event> expectedEvents = new ArrayList<>();

    @BeforeAll
    public void setup() throws IOException {
        expectedEvents.addAll(IOTestUtil.getPseudoRandomEvents(15));
        WRITER.save(expectedEvents, FILE);
    }

    @AfterAll
    void tearDown() throws IOException {
        EventCollectionJsonWriter writer = new EventCollectionJsonWriter();
        writer.save(new ArrayList<Event>(), FILE);
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        actualEvents = READER.load(FILE);
    }

    @AfterAll
    public void tearDown() throws IOException {
        WRITER.save(new ArrayList<Event>(), FILE);
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

    @ParameterizedTest
    @NullSource
    public void testLoad_doesNotThrowOnNullInput(File input) throws IOException {
        Collection<Event> events = READER.load();
        assertDoesNotThrow(() -> WRITER.save(events, null));
    }

    @Test
    public void testLoad_throwsFileNotFoundExceptionOnNonexistentFile() {
        File nonExistentFile = new File("./ThisIsNotAFile.json");
        assertThrows(FileNotFoundException.class, () -> WRITER.save(actualEvents, nonExistentFile));
    }

    @Test
    public void testLoad_throwsOnInvalidFileType() throws IOException {
        File file = File.createTempFile("tempfile", null);
        assertThrows(IllegalArgumentException.class, () -> WRITER.save(actualEvents, file));
    }

}
