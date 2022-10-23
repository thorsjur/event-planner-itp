package eventplanner.rest;

import java.io.File;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eventplanner.core.User;
import eventplanner.json.util.IOUtil;

@RestController
public class UserController {

	private File file = new File("eventplanner/rest/src/main/data/users.json"); //TODO kan endre dette i reader/writerne

	@GetMapping("/user")
	public User user(@RequestParam(value = "email", defaultValue = " ") String email) {
		try {
			return IOUtil.loadUserMatchingEmail(email, file);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/user/create")
	public void create(@RequestParam String email, @RequestParam String password, @RequestParam boolean above18) {
		try {
			IOUtil.appendUserToFile(new User(email, password, above18), file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
