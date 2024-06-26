package User;

import junit.framework.TestCase;

public class TestUser extends TestCase {
    public void testUser() {
        User user1 = new User("duta", "duta123123", "example@mail");
        assertTrue(user1.isPasswordCorrect("duta123123"));
        assertFalse(user1.isPasswordCorrect("duta12312"));
        assertEquals("duta", user1.getUserName());
        assertEquals("example@mail", user1.getEmail());
    }
}
