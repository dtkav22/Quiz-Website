package User;

import Quiz.QuizTask;
import junit.framework.TestCase;

import java.sql.SQLException;
import java.util.ArrayList;

public class TestUserDAO extends TestCase {
    private User user;
    private UserDAO userDAO;
    public void setUp() {
        user = new User("duta1", "duta", "example@example");
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

    public void testGetPerformanceHistory() throws SQLException {
        ArrayList<Performance> set = userDAO.getUserPerformanceHistory("1");
        assertEquals("1", set.get(0).getQuiz_id());
        assertEquals(100.0, set.get(0).getScore());
        System.out.println(set.get(0).getDate());
    }

    public void testGetFriendsForUser1() throws SQLException {
        ArrayList<User> list = userDAO.getFriendsForUser("Mariami");
        assertEquals(2, list.size());
        assertEquals("duta", list.get(0).getUserName());
        assertEquals("Ioane", list.get(1).getUserName());
    }

    public void testGetFriendsForUser2() throws SQLException {
        ArrayList<User> list = userDAO.getFriendsForUser("duta");
        assertEquals(1, list.size());
        assertEquals("Mariami", list.get(0).getUserName());
    }

    public void testGetFriendsForUser3() throws SQLException {
        ArrayList<User> list = userDAO.getFriendsForUser("Ioane");
        assertEquals(1, list.size());
        assertEquals("Mariami", list.get(0).getUserName());
    }

}
