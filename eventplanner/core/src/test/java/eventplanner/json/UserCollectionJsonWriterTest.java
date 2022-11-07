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
import eventplanner.core.User;

@TestInstance(Lifecycle.PER_CLASS)
class UserCollectionJsonWriterTest {

    static final String FILE_PATH = "src/test/java/resources/data/user.json";
    private static final File FILE = new File(FILE_PATH);
    private static final UserCollectionJsonWriter WRITER = new UserCollectionJsonWriter();
    private static final UserCollectionJsonReader READER = new UserCollectionJsonReader();

    private Collection<User> actualUsers;

    // Gets read before every test, assuming UserCollectionJsonReader works as
    // expected
    private final Collection<User> expectedUsers = new ArrayList<>();

    @BeforeAll
    public void setup() throws IOException {
        expectedUsers.addAll(IOTestUtil.getPseudoRandomUsers(15));
        WRITER.save(expectedUsers, FILE);
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        actualUsers = READER.load(FILE);
    }

    @AfterAll
    public void tearDown() throws IOException {
        WRITER.save(new ArrayList<User>(), FILE);
    }


    @Test
    void testSave_retainsUserData() {
        assertTrue(actualUsers.size() == expectedUsers.size());
        assertTrue(
                expectedUsers.stream()
                        .allMatch(expected -> actualUsers.contains(expected)));
    }

    @ParameterizedTest
    @NullSource
    public void testLoad_doesNotThrowOnNullInput(File input) throws IOException {
        Collection<User> users = READER.load(input);
        assertDoesNotThrow(() -> WRITER.save(users, null));
    }

    @Test
    public void testLoad_throwsFileNotFoundExceptionOnNonexistentFile() {
        File nonExistentFile = new File("./ThisIsNotAFile.json");
        assertThrows(FileNotFoundException.class, () -> WRITER.save(actualUsers, nonExistentFile));
    }

    @Test
    public void testLoad_throwsOnInvalidFileType() throws IOException {
        File file = File.createTempFile("tempfile", null);
        assertThrows(IllegalArgumentException.class, () -> WRITER.save(actualUsers, file));
    }
}
