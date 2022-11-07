package eventplanner.json;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

@TestInstance(Lifecycle.PER_CLASS)
public class UserCollectionJsonReaderTest {

    private static final UserCollectionJsonReader READER = new UserCollectionJsonReader();

    // NOTE: Further testing of this class is done in the
    // UserCollectionJsonWriterTest class, because of cyclic dependencies between
    // the classes. The reader needs the writer to test itself, and the writer needs
    // the reader to test itself.

    @ParameterizedTest
    @NullSource
    public void testLoad_doesNotThrowOnNullInput(File input) {
        assertDoesNotThrow(() -> READER.load(null));
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
