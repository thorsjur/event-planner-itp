package eventplanner.core;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * Test class for User class in core module
 */
public class UserTest {

    @Test
    public void createUser_ok(){
        User user1 = new User("User1@test.test", "password", true); //TODO - pass på følgefeil
        Assert.assertEquals("User1@test.test", user1.email());
        Assert.assertNotEquals("user1@test.test", user1.email());
    }

    @Test
    public void createUser_throwsIllegalArgumentException(){ //TODO - trenger kanskje flere tester her
        Assert.assertThrows(IllegalArgumentException.class, () -> new User(null, null, null));
        Assert.assertThrows(IllegalArgumentException.class, () -> new User("", null, null));
        Assert.assertThrows(IllegalArgumentException.class, () -> new User(" ", null, null));
    }

    @Test 
    public void createUser_checksExceptionMessage(){ //TODO - trenger kanskje flere tester her
        Exception e1 = Assert.assertThrows(IllegalArgumentException.class, () -> new User(null, null, null));
        Exception e2 = Assert.assertThrows(IllegalArgumentException.class, () -> new User("", null, null));
        Exception e3 = Assert.assertThrows(IllegalArgumentException.class, () -> new User(" ", null, null));
        Assert.assertTrue(e1.getMessage().equals("Email is null or blank"));
        Assert.assertTrue(e2.getMessage().equals("Email is null or blank"));
        Assert.assertTrue(e3.getMessage().equals("Email is null or blank"));
    }
}
