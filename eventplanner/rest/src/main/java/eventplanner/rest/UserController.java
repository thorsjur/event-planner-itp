package eventplanner.rest;

import java.io.File;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eventplanner.core.User;
import eventplanner.json.util.IOUtil;

@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping
	public User user(@RequestParam(value = "email", defaultValue = " ") String email) {
		try {
			return IOUtil.loadUserMatchingEmail(email, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/create")
	public void create(@RequestParam String email, @RequestParam String password, @RequestParam boolean above18) {
		try {
			IOUtil.appendUserToFile(new User(email, password, above18), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
