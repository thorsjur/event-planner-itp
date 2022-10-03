package eventplanner.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.core.type.TypeReference;

import eventplanner.core.Event;

public class EventCollectionJsonReader {
    
    private static final CustomObjectMapper OBJECT_MAPPER = new CustomObjectMapper();

    public Collection<Event> load(File file) throws IOException {
        if (file == null) file = new File(EventCollectionJsonWriter.DIRECTORY_PATH + EventCollectionJsonWriter.DEFAULT_FILE_NAME + EventCollectionJsonWriter.FILE_EXTENSION);
        if (!file.exists()) throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        
        return OBJECT_MAPPER.readValue(file, new TypeReference<Collection<Event>>(){});
    }

    public Collection<Event> load() throws IOException {
        return load(null);
    }

}
