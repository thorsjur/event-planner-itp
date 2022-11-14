package eventplanner.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eventplanner.core.User;
import eventplanner.json.util.IOUtil;

/**
 * Writes user data to a json file using a {@link CustomObjectMapper}.
 */
public class UserCollectionJsonWriter {

    public static final String FILE_EXTENSION = ".json";

    private static final CustomObjectMapper OBJECT_MAPPER = new CustomObjectMapper();

    /**
     * Method to save a collection of users to a JSON file. If no file is specified
     * for the method, the writer writes to the default JSON file specified by the
     * classes constant fields.
     * 
     * @param file the json file to save events to
     * @throws IOException on I/O errors
     */
    public void save(final Collection<User> collection, File file) throws IOException {
        if (file == null) {
            file = getDefaultFile();
        }
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        }
        if (!IOUtil.hasFileExtension(file, FILE_EXTENSION)) {
            throw new IllegalArgumentException("File is not of type .json");
        }
        OBJECT_MAPPER.writeValue(file, collection);
    }

    /**
     * Method to save a collection of users to the default JSON file.
     * 
     * @see UserCollectionJsonWriter#save(Collection collection, File file)
     */
    public void save(final Collection<User> collection) throws IOException {
        save(collection, null);
    }

    /**
     * Method to save a single user to the specified JSON file.
     * 
     * @see UserCollectionJsonWriter#save(Collection collection, File file)
     */
    public void save(final User user, File file) throws IOException {
        save(new ArrayList<>(List.of(user)), file);
    }

    /**
     * Method to save a single user to the default JSON file.
     * 
     * @see UserCollectionJsonWriter#save(Collection collection, File file)
     */
    public void save(final User user) throws IOException {
        save(user, null);
    }

    private File getDefaultFile() {
        String[] segments = { "eventplanner", "core", "src", "main", "java", "resources", "data", "user.json" };
        Path path = IOUtil.getPathRelativeToProjectRoot(segments);
        return path.toFile();
    }

}
