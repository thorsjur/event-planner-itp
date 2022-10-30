package eventplanner.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eventplanner.core.User;
import eventplanner.json.util.IOUtil;

@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping("")
	public void connect() {
		return;
	}

	@GetMapping("/get")
	public User user(@RequestParam(value = "email", defaultValue = " ") String email) {
		try {
			return IOUtil.loadUserMatchingEmail(email, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping(path="/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean create(@RequestBody User user) {
		try {
			IOUtil.appendUserToFile(user, null);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
