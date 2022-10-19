package eventplanner.json;

import eventplanner.core.User;
import eventplanner.json.util.IOUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Writes data to a json file using a {@link CustomObjectMapper}.
 */
public class UserCollectionJsonWriter {

    public static final String DIRECTORY_PATH = "src/main/resources/data/";
    public static final String DEFAULT_FILE_NAME = "user";
    public static final String FILE_EXTENSION = ".json";

    private static final CustomObjectMapper OBJECT_MAPPER = new CustomObjectMapper();

    /**
     * Method to save a collection of events to a JSON file.
     * If no file is specified for the method, the writer writes to the default
     * JSON file specified by the classes constant fields.
     * 
     * @param file the json file to save events to
     * @throws IOException on I/O errors
     */
    public void save(final Collection<User> collection, File file) throws IOException {
        if (file == null) {
            file = new File(DIRECTORY_PATH + DEFAULT_FILE_NAME + FILE_EXTENSION);
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
     * Method to save a collection of events to the default JSON file.
     * 
     * @see EventCollectionJsonWriter#save(Collection collection, File file)
     */
    public void save(final Collection<User> collection) throws IOException {
        save(collection, null);
    }

    /**
     * Method to save a single event to the specified JSON file.
     * 
     * @see EventCollectionJsonWriter#save(Collection collection, File file)
     */
    public void save(final User user, File file) throws IOException {
        save(new ArrayList<User>(List.of(user)), file);
    }

    /**
     * Method to save a single event to the default JSON file.
     * 
     * @see EventCollectionJsonWriter#save(Collection collection, File file)
     */
    public void save(final User user) throws IOException {
        save(user, null);
    }

}
