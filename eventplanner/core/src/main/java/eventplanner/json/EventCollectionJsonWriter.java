package eventplanner.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eventplanner.core.Event;
import eventplanner.json.util.IOUtil;

/**
 * Writes data to a json file using a {@link CustomObjectMapper}.
 */
public class EventCollectionJsonWriter {

    public static final String FILE_EXTENSION = ".json";
    private static final CustomObjectMapper OBJECT_MAPPER = new CustomObjectMapper();
    private File file;

    /**
     * Method to save a collection of events to a JSON file.
     * If no file is specified for the method, the writer writes to the default
     * JSON file specified by the classes constant fields.
     * 
     * @param file the json file to save events to
     * @throws IOException on I/O errors
     */
    public void save(final Collection<Event> collection, File file) throws IOException {

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
     * Method to save a collection of events to the default JSON file.
     * 
     * @see EventCollectionJsonWriter#save(Collection collection, File file)
     */
    public void save(final Collection<Event> collection) throws IOException {
        save(collection, null);
    }

    /**
     * Method to save a single event to the specified JSON file.
     * 
     * @see EventCollectionJsonWriter#save(Collection collection, File file)
     */
    public void save(final Event event, File file) throws IOException {
        save(new ArrayList<>(List.of(event)), file);
    }

    /**
     * Method to save a single event to the default JSON file.
     * 
     * @see EventCollectionJsonWriter#save(Collection collection, File file)
     */
    public void save(final Event event) throws IOException {
        save(event, null);
    }

    private File getDefaultFile() {
        String[] segments = { "eventplanner", "core", "src", "main", "java", "resources", "data", "event.json" };
        Path path = IOUtil.getPathRelativeToProjectRoot(segments);
        return path.toFile();
    }

}
