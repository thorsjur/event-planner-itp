package eventplanner.rest;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eventplanner.core.User;

@RestController
public class UserController {

	// private static final String template = "Hello, %s!";
	// private final AtomicLong counter = new AtomicLong();

	@GetMapping("/user")
	public User user(@RequestParam(value = "email") String email) {

		return new User("email", "email", true);


		// return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}
