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

import eventplanner.core.User;

@TestInstance(Lifecycle.PER_CLASS)
class UserCollectionJsonWriterTest {

    static final String FILE_PATH = "src/test/java/resources/data/user.json";
    private static final File FILE = new File(FILE_PATH);

    private Collection<User> actualUsers;

    // Gets read before every test, assuming UserCollectionJsonReader works as
    // expected
    private final Collection<User> expectedUsers = new ArrayList<>();

    @BeforeAll
    void setup() throws IOException {
        UserCollectionJsonWriter writer = new UserCollectionJsonWriter();
        expectedUsers.addAll(IOTestUtil.getPseudoRandomUsers(15));
        writer.save(expectedUsers, FILE);
    }

    @AfterAll
    void tearDown() throws IOException {
        UserCollectionJsonWriter writer = new UserCollectionJsonWriter();
        writer.save(new ArrayList<User>(), FILE);
    }

    @BeforeEach
    void beforeEach() throws IOException {
        UserCollectionJsonReader reader = new UserCollectionJsonReader();
        actualUsers = reader.load(FILE);
    }

    @Test
    void testSave_retainsUserData() {
        assertTrue(actualUsers.size() == expectedUsers.size());
        assertTrue(
                expectedUsers.stream()
                        .allMatch(expected -> actualUsers.contains(expected)));
    }
}
