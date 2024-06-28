package User;

import junit.framework.TestCase;

public class TestUser extends TestCase {
    public void testUser() {
        User user1 = new User("duta", "duta", "example@mail", false);
        assertTrue(user1.isPasswordCorrect("duta"));
        assertFalse(user1.isPasswordCorrect("duta12312"));
        assertEquals("duta", user1.getUserName());
        assertEquals("example@mail", user1.getEmail());
        System.out.println(user1.getPassword());
    }
}
