package eventplanner.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eventplanner.core.Event;
import eventplanner.core.util.EventUtil;
import eventplanner.json.util.IOUtil;

@RestController
public class EventController {

	private File file = new File("eventplanner/rest/src/main/data/events.json"); //TODO kan endre dette i reader/writerne

	@GetMapping("/event")
	public Event event(@RequestParam String eventName) {
        try {
            return IOUtil.loadEventMatchingName(eventName, file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

    @GetMapping("/event/all")
    public Collection<Event> all() {
        try {
            return IOUtil.loadAllEvents(file);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Event>();
        }
    }

    @PutMapping(path="/event/update", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    public void add(@RequestBody Event event) {
        System.out.println(event.getName());
        try {
            IOUtil.updateEvent(event, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostMapping("/event/create")
	public void create(@RequestParam Map<String, String> params) {
    
        try {
            IOUtil.appendEventToFile(EventUtil.createEventFromParams(params), file);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}


}
