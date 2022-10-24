package eventplanner.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eventplanner.core.Event;
import eventplanner.core.User;
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

    @PostMapping("/event/addUser")
    public void add(@RequestParam String eventName, @RequestParam String userEmail) {
        try {
            Event event = IOUtil.loadEventMatchingName(eventName, file);
            User user = IOUtil.loadUserMatchingEmail(userEmail, file);
            IOUtil.addUserToEvent(event, user, file);
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

    @DeleteMapping("/event/remove")
    public void remove(@RequestParam String eventName, @RequestParam String userEmail) {
        try {
            Event event = IOUtil.loadEventMatchingName(eventName, file);
            User user = IOUtil.loadUserMatchingEmail(userEmail, file);
            IOUtil.removeUserFromEvent(event, user, file);
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }

    //TODO - alter events?
}
