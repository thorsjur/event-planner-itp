package eventplanner.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

import com.fasterxml.jackson.core.type.TypeReference;

import eventplanner.core.Event;
import eventplanner.json.util.IOUtil;

/**
 * Reads data from a JSON file using {@link CustomObjectMapper}.
 */
public class EventCollectionJsonReader {

    private static final CustomObjectMapper OBJECT_MAPPER = new CustomObjectMapper();

    /**
     * Method to load a collection of events from a JSON file. If no file is
     * specified for the method, the reader reads from the default JSON file
     * specified in {@link EventCollectionJsonWriter}
     * 
     * @param file the json file to load events from.
     * @return a collection of events
     * @throws IOException on low level I/O errors
     */
    public Collection<Event> load(File file) throws IOException {

        if (file == null) {
            file = getDefaultFile();
        }
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        }
        if (!IOUtil.hasFileExtension(file, EventCollectionJsonWriter.FILE_EXTENSION)) {
            throw new IllegalArgumentException("File is not of type .json");
        }

        return OBJECT_MAPPER.readValue(file, new TypeReference<Collection<Event>>() {
        });
    }

    /**
     * Method to load a collection of events using the default JSON file.
     * 
     * @see EventCollectionJsonReader#load(File file)
     */
    public Collection<Event> load() throws IOException {
        return load(null);
    }

    private File getDefaultFile() {
        String[] segments = { "eventplanner", "core", "src", "main", "java", "resources", "data", "event.json" };
        Path path = IOUtil.getPathRelativeToProjectRoot(segments);
        return path.toFile();
    }
    
}
