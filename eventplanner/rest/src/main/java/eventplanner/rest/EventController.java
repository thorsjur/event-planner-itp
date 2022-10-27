package eventplanner.rest;

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

@RestController
@RequestMapping("/event")
public class EventController {

    @GetMapping
    public Event event(@RequestParam String eventName) {
        try {
            return IOUtil.loadEventMatchingName(eventName, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/all")
    public Collection<Event> all() {
        try {
            return IOUtil.loadAllEvents(null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Event>();
        }
    }

    @PutMapping(path = "/update",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean add(@RequestBody Event event) {
        try {
            IOUtil.updateEvent(event, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping(path = "/create",
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean create(@RequestBody Event event) {
        try {
            IOUtil.appendEventToFile(event, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {

        try {
            IOUtil.deleteEventFromFile(id, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
