package json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import EventPlanner.Event;

public class EventCollectionJsonWriter {
    
    public static final String DIRECTORY_PATH = "src/main/resources/data/";
    public static final String DEFAULT_FILE_NAME = "event";
    public static final String FILE_EXTENSION = ".json";

    private static final CustomObjectMapper OBJECT_MAPPER = new CustomObjectMapper();

    public void save(final Collection<Event> collection, File file) throws IOException {
        if (file == null) file = new File(DIRECTORY_PATH + DEFAULT_FILE_NAME + FILE_EXTENSION);
        if (!file.exists()) throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        OBJECT_MAPPER.writeValue(file, collection);
    }

    public void save(final Collection<Event> collection) throws IOException {
        save(collection, null);
    }

    public void save(final Event event, File file) throws IOException {
        save(new ArrayList<Event>(List.of(event)), file);
    }

    public void save(Event event) throws IOException {
        save(event, null);
    }

}
