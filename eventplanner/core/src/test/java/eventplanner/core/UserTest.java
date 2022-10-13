package eventplanner.core;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for User class in core module
 */
public class UserTest {

    @Test
    public void createUser_ok(){
        User user1 = new User("User1");
        Assert.assertEquals("User1", user1.username());
        Assert.assertNotEquals("user1", user1.username());
    }

    @Test
    public void createUser_throwsIllegalArgumentException(){
        Assert.assertThrows(IllegalArgumentException.class, () -> new User(null));
        Assert.assertThrows(IllegalArgumentException.class, () -> new User(""));
        Assert.assertThrows(IllegalArgumentException.class, () -> new User(" "));
    }

    @Test 
    public void createUser_checksExceptionMessage(){
        Exception e1 = Assert.assertThrows(IllegalArgumentException.class, () -> new User(null));
        Exception e2 = Assert.assertThrows(IllegalArgumentException.class, () -> new User(""));
        Exception e3 = Assert.assertThrows(IllegalArgumentException.class, () -> new User(" "));
        Assert.assertTrue(e1.getMessage().equals("User name is null or blank"));
        Assert.assertTrue(e2.getMessage().equals("User name is null or blank"));
        Assert.assertTrue(e3.getMessage().equals("User name is null or blank"));
    }
}
