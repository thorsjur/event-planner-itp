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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import eventplanner.core.Event;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventCollectionJsonReaderTest {

    private static final File FILE = new File(EventCollectionJsonWriterTest.FILE_PATH);
    private static final EventCollectionJsonReader READER = new EventCollectionJsonReader();
    private static final EventCollectionJsonWriter WRITER = new EventCollectionJsonWriter();
    private Collection<Event> expectedEvents = new ArrayList<>();
    private Collection<Event> actualEvents = new ArrayList<>();

    @BeforeAll
    public void setup() throws IOException {
        expectedEvents.addAll(IOTestUtil.getPseudoRandomEvents(15));
        WRITER.save(expectedEvents, FILE);
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
    void testLoad_dataIsUnchangedFromSave() {
        assertTrue(
                expectedEvents.stream()
                        .allMatch(expected -> {
                            return actualEvents.stream()
                                    .anyMatch(actual -> IOTestUtil.compareEventsUsersExcluded(actual, expected));
                        }));
    }

    @ParameterizedTest
    @NullSource
    public void testLoad_doesNotThrowOnNullInput(File input) {
        assertDoesNotThrow(() -> READER.load(input));
    }

    @Test
    public void testLoad_throwsFileNotFoundExceptionOnNonexistentFile() {
        File nonExistentFile = new File("./ThisIsNotAFile.json");
        assertThrows(FileNotFoundException.class, () -> READER.load(nonExistentFile));
    }

    @Test
    public void testLoad_throwsOnInvalidFileType() throws IOException {
        File file = File.createTempFile("tempfile", null);
        assertThrows(IllegalArgumentException.class, () -> READER.load(file));
    }

}
