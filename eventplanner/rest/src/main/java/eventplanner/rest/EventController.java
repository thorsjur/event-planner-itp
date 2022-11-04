package eventplanner.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eventplanner.core.Event;
import eventplanner.json.util.IOUtil;

/**
 * API requests containing "/event" get 
 * sent to this class. Which has methods for requests expanding
 * on "/event", with functionality that controls event manipulation
 */
@RestController
@RequestMapping("/event")
public class EventController {

    /**
     * Method to get an event.
     * 
     * @param eventName the name of the event to be acquired
     * @return the user with a matching email, null if no user has the given email 
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Event event(@RequestParam String id) {
        try {
            return IOUtil.loadEventMatchingId(id, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to get all events that are saved.
     * 
     * @return a collection of events 
     */
    @GetMapping(path = "/all",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Event> all() {
        try {
            return IOUtil.loadAllEvents(null);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Event>();
        }
    }

    /**
     * Method to update an already existing event.
     * 
     * @param event the event of be updated
     * @return a boolean of whether the event got updated
     */
    @PutMapping(path = "/update",
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean add(@RequestBody Event event) {
        try {
            IOUtil.updateEvent(event, null);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method to create an event.
     * 
     * @param event the event to be created
     * @return a boolean of whether the event got created or not 
     */
    @PostMapping(path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean create(@RequestBody Event event) {
        try {
            IOUtil.appendEventToFile(event, null);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method to delete an event.
     * 
     * @param id the id of the event to be deleted
     * @return a boolean of whether the event got deleted or not 
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        try {
            IOUtil.deleteEventFromFile(id, null);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
