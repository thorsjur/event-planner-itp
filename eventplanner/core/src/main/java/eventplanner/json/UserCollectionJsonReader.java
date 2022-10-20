package eventplanner.json;

import com.fasterxml.jackson.core.type.TypeReference;

import eventplanner.core.User;
import eventplanner.json.util.IOUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Reads data from a JSON file using the {@link CustomObjectMapper}.
 */
public class UserCollectionJsonReader {

    private static final CustomObjectMapper USER_MAPPER = new CustomObjectMapper();

    /**
     * Method to load a collection of users from a JSON file.
     * If no file is specified for the method, the reader reads from the default
     * JSON file specified in {@link UserCollectionJsonWriter}
     * 
     * @param file the json file to load users from.
     * @return a collection of users
     * @throws IOException on low level I/O errors
     */
    public Collection<User> load(File file) throws IOException {
        if (file == null) {
            file = new File(UserCollectionJsonWriter.DIRECTORY_PATH
                    + UserCollectionJsonWriter.DEFAULT_FILE_NAME
                    + UserCollectionJsonWriter.FILE_EXTENSION);
        }
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        }
        if (!IOUtil.hasFileExtension(file, UserCollectionJsonWriter.FILE_EXTENSION)) {
            throw new IllegalArgumentException("File is not of type .json");
        }
        if (file.length() < 10) {
            return new ArrayList<User>();
        }
        return USER_MAPPER.readValue(file, new TypeReference<Collection<User>>() {
        });
    }

    /**
     * Method to load a collection of users using the default JSON file.
     * 
     * @see UserCollectionJsonReader#load(File file)
     */
    public Collection<User> load() throws IOException {
        return load(null);
    }

}
