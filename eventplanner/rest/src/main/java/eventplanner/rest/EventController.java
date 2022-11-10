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
 * RestController for handling all requests concerning events.
 * Uses {@code /event} as base path.
 */
@RestController
@RequestMapping("/event")
public class EventController {

    /**
     * Handler method for handling GET requests from a given event id.
     * Request syntax: {@code SERVICE_PATH + /event?id= + EVENT_UUID}
     * 
     * @param id the id of the event to be acquired
     * @return the event in json format with a matching id, null if no event has the given id 
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Event get(@RequestParam String id) {
        try {
            return IOUtil.loadEventMatchingId(id, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Handler method for handling GET requests returning all events saved.
     * Request syntax: {@code SERVICE_PATH + /event/all}
     * 
     * @return a collection of all events in json format
     */
    @GetMapping(path = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Event> all() {
        try {
            return IOUtil.loadAllEvents(null);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Handler method for handling PUT requests that updates a specific event.
     * Request syntax: {@code SERVICE_PATH + /event/update}
     * 
     * @param event the event in json format to be updated
     * @return a boolean value indicating whether the handler was successful
     */
    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean update(@RequestBody Event event) {
        try {
            IOUtil.updateEvent(event, null);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Handler method for handling POST requests that creates a new event.
     * Request syntax: {@code SERVICE_PATH + /event/create}
     * 
     * @param event the event in json format to be created
     * @return a boolean value indicating whether the handler was successful
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
     * Handler method for handling DELETE requests from a given event id.
     * Request syntax: {@code SERVICE_PATH + /event/ + EVENT_UUID}
     * 
     * @param id the id of the event to be deleted
     * @return a boolean value indicating whether the handler was successful
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
