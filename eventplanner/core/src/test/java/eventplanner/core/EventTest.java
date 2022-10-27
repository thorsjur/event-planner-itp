package eventplanner.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import eventplanner.json.IOTestUtil;

/**
 * Test class for Event class in core module
 */
public class EventTest {

	private static final LocalDateTime NOW = LocalDateTime.now();
	private static final LocalDateTime NOW_PLUS = NOW.plusHours(10);
	private static Event event;
	private List<User> users;
	private EventType type;
	

	@BeforeEach
	public void beforeEach() {
		users = IOTestUtil.getPseudoRandomUsers(5);
		type = getRandomEventType();
		event = new Event(type, "test_name", NOW, NOW_PLUS, "test_location", users, null, null);
	}

	@ParameterizedTest
	@NullAndEmptySource
	public void testConstructor_throwsIllegalArgumentExceptionOnNullOrEmptyStrings(String input) {
		assertThrows(IllegalArgumentException.class, () -> new Event(type, input, NOW, NOW_PLUS, "loc", users, null, null));
		assertThrows(IllegalArgumentException.class, () -> new Event(type, "name", NOW, NOW_PLUS, input, users, null, null));
	}

	@Test
	public void testConstructor_throwsIllegalArgumentExceptionOnInvalidNullInput() {
		assertThrows(IllegalArgumentException.class, () -> new Event(null, "name", NOW, NOW_PLUS, "loc", users, "author", "desc"));
		assertThrows(IllegalArgumentException.class, () -> new Event(type, "name", NOW, null, "loc", users, "author", "desc"));
		assertThrows(IllegalArgumentException.class, () -> new Event(type, "name", null, null, "loc"));
		assertDoesNotThrow(() -> new Event(type, "name", NOW, NOW_PLUS, "loc", null, null, null));
	}

	@ParameterizedTest
	@NullSource
	public void testAddUser_throwsExceptionOnNullUser(User user) {
		assertThrows(IllegalArgumentException.class, () -> event.addUser(user));
	}

	@ParameterizedTest
	@NullSource
	public void testRemoveUser_throwsExceptionOnNullUser(User user) {
		assertThrows(IllegalArgumentException.class, () -> event.removeUser(user));
	}

	@Test
	public void testAddUser_addsUser() {
		int userCount = event.getUsers().size();
		User user = new User("test@example.com", "password", true);
		event.addUser(user);

		assertEquals(userCount + 1, event.getUsers().size());
		assertEquals(user, event.getUsers().get(event.getUsers().size() - 1));
	}

	@Test
	public void testAddUser_doesNotAddDuplicateUsers() {
		int userCount = event.getUsers().size();
		User user = new User("test@example.com", "password", true);
		User user2 = new User("test@example.com", "password", true);
		event.addUser(user);

		assertEquals(userCount + 1, event.getUsers().size());
		assertEquals(user, event.getUsers().get(event.getUsers().size() - 1));

		event.addUser(user2);
		assertEquals(userCount + 1, event.getUsers().size());
	}

	@Test
	public void testRemoveUser_removesUser() {
		User user = event.getUsers().get(0);
		int userCount = event.getUsers().size();
		event.removeUser(user);
		
		assertEquals(userCount - 1, event.getUsers().size());
		assertFalse(user == event.getUsers().get(0));
	}

	@Test
	public void testRemoveUser_doesNotThrowIfNoSuchUser() {
		User user = new User("no@such.email", "no_such_password", false);
		assertDoesNotThrow(() -> event.removeUser(user));
	}

	@Test
	public void testEquals() {
		Event ev1 = getSimpleEvent();
		Event ev2 = getSimpleEvent();

		assertEquals(ev1.getType(), ev2.getType());
		assertEquals(ev1.getName(), ev2.getName());
		assertEquals(ev1.getStartDate(), ev2.getStartDate());
		assertEquals(ev1.getEndDate(), ev2.getEndDate());
		assertEquals(ev1.getLocation(), ev2.getLocation());
	}

	private static EventType getRandomEventType() {
		Random random = new Random(10101);
		return EventType.values()[random.nextInt(EventType.values().length)];
	}

	private static Event getSimpleEvent() {
		return new Event(
			event.getType(),
			event.getName(),
			event.getStartDate(),
			event.getEndDate(),
			event.getLocation(),
			List.of(
				new User("test@example.com", "password", false),
				new User("example@test.com", "dworssap", false)
				),
			event.getAuthorEmail(),
			event.getDescription()
		);
	}
}