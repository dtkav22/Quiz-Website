package User;

import junit.framework.TestCase;

import java.sql.SQLException;

public class TestUserDAO extends TestCase {
    private User user;
    private UserDAO userDAO;
    public void setUp() {
        user = new User("duta", "duta", "example@example");
        userDAO = new UserDAO();
    }
    public void testAddUser() throws SQLException {
        userDAO.addUser(user);
        String id = userDAO.getUserId(user.getUserName());
        User userClone = userDAO.getUser(id);
        assertEquals(userClone.getUserName(), user.getUserName());

        user = new User("mate", "mate", "example@example");
        userDAO.addUser(user);
        id = userDAO.getUserId(user.getUserName());
        userClone = userDAO.getUser(id);
        assertEquals("mate", userClone.getUserName());
    }
}
