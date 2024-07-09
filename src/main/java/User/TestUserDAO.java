package User;

import DataBaseConnectionPool.DataBaseConnectionPool;
import Quiz.QuizTask;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestUserDAO extends TestCase {
    private User user;
    private UserDAO userDAO;
    public void setUp() {
        userDAO = new UserDAO();
    }

    public void testFriendsMethods() throws SQLException {
        ArrayList<String> list = userDAO.getFriendRequestsForUser("3");
        assertEquals(1, list.size());
        assertEquals("1", list.get(0));

        ArrayList<String> list2 = userDAO.getSentRequestsForUser("1");
        assertEquals(1, list2.size());
        assertEquals("3", list2.get(0));
    }

    public void testGetChallengesSentForUser() throws SQLException {
        String user_id = "2";
        ArrayList<Challenge> challenges = userDAO.getChallengesSentForUser(user_id);
        assertEquals(3,challenges.size());
        assertEquals("3",challenges.get(0).getUser1_id());
        assertEquals("2",challenges.get(0).getUser2_id());
        assertEquals("1",challenges.get(0).getQuiz_id());

        assertEquals("1",challenges.get(1).getUser1_id());
        assertEquals("2",challenges.get(1).getUser2_id());
        assertEquals("1",challenges.get(1).getQuiz_id());

        assertEquals("1",challenges.get(2).getUser1_id());
        assertEquals("2",challenges.get(2).getUser2_id());
        assertEquals("2",challenges.get(2).getQuiz_id());
    }

    public void testGetReceivedMailsForUser1() throws SQLException {
        String user_id = "3";
        ArrayList<Mail> mails = userDAO.getReceivedMailsForUser(user_id);
        assertEquals(3, mails.size());
        assertEquals("2", mails.get(0).getSender_id());
        assertEquals("2", mails.get(1).getSender_id());
        assertEquals("2", mails.get(2).getSender_id());

        assertEquals("3", mails.get(0).getReceiver_id());
        assertEquals("3", mails.get(1).getReceiver_id());
        assertEquals("3", mails.get(2).getReceiver_id());


        assertEquals("Hi!", mails.get(0).getMail_text());
        assertEquals("I am coding", mails.get(1).getMail_text());
        assertEquals("and you?", mails.get(2).getMail_text());
    }

    public void testAddUser() throws SQLException {
        user = new User("duta1", "duta", "example@example", false);
        userDAO.addUser(user);
        String id = userDAO.getUserId(user.getUserName());
        User userClone = userDAO.getUser(id);
        assertEquals(userClone.getUserName(), user.getUserName());

        user = new User("mate", "mate", "example@example", false);
        userDAO.addUser(user);
        id = userDAO.getUserId(user.getUserName());
        userClone = userDAO.getUser(id);
        assertEquals("mate", userClone.getUserName());
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "DELETE FROM users_table WHERE username= ? OR username= ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1,"mate");
        statement.setString(2, "duta1");
        statement.executeUpdate();
        assertNull(userDAO.getUser("0"));
    }
    public void testGetFriendsForUser1() throws SQLException {
        ArrayList<String> list = userDAO.getFriendsForUser("2");
        assertEquals(2, list.size());
        assertEquals("1", list.get(0));
        assertEquals("3", list.get(1));
    }

    public void testGetFriendsForUser2() throws SQLException {
        ArrayList<String> list = userDAO.getFriendsForUser("1");
        assertEquals(1, list.size());
        assertEquals("2", list.get(0));
    }

    public void testGetFriendsForUser3() throws SQLException {
        ArrayList<String> list = userDAO.getFriendsForUser("3");
        assertEquals(1, list.size());
        assertEquals("2", list.get(0));
    }

    //while request can be sent
    public void testSendFriendRequest1() throws SQLException {
        String sender_id = "1";
        String reciever_id = "5";
        assertTrue(userDAO.sendFriendRequest(sender_id, reciever_id));
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT user1_id, user2_id, isPending FROM relations_table WHERE user1_id = " + sender_id + " AND user2_id = " + reciever_id;
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        rs.next();
        assertEquals(1, rs.getInt("isPending"));
        System.out.println("Friend request has been sent.");
    }

    //while request can't be sent
    public void testSendFriendRequest2() throws SQLException {
        String sender_id = "1";
        String reciever_id = "2";
        assertFalse(userDAO.sendFriendRequest(sender_id, reciever_id));
        System.out.println("Given two people are already friends or friend request is already sent");
    }

    public void testAcceptFriendRequest() throws SQLException {
        String sender_id = "5";
        String reciever_id = "6";
        userDAO.sendFriendRequest(sender_id, reciever_id);
        userDAO.acceptFriendRequest(sender_id, reciever_id);
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT user1_id, user2_id, isPending FROM relations_table WHERE user1_id = " + sender_id + " AND user2_id = " + reciever_id;
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        rs.next();
        assertEquals(0,rs.getInt("isPending"));
        System.out.println("They are now friends");
    }

    public void testAreFriends1() throws SQLException {
        String user1_id = "1";
        String user2_id = "2";
        assertTrue(userDAO.areFriends(user1_id, user2_id));
    }

    public void testAreFriends2() throws SQLException {
        String user1_id = null;
        String user2_id = "2";
        assertFalse(userDAO.areFriends(user1_id, user2_id));
    }

    public void testAreFriends3() throws SQLException {
        String user1_id = "1";
        String user2_id = "3";
        assertFalse(userDAO.areFriends(user1_id, user2_id));
    }

    public void testSendChallenge1() throws SQLException {
        String quiz_id = "1";
        String user1_id = "2";
        String user2_id = "3";
        userDAO.sendChallenge(user1_id, user2_id, quiz_id);
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM challenges_table WHERE user1_id = " + user1_id + " AND user2_id = " + user2_id + " AND quiz_id = " + quiz_id;
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        assertTrue(rs.next());
        System.out.println("Challenge has sent");
    }

    public void testSendChallenge2() throws SQLException {
        String quiz_id = "1";
        String user1_id = "1";
        String user2_id = "6";
        userDAO.sendChallenge(user1_id, user2_id, quiz_id);
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM challenges_table WHERE user1_id = " + user1_id + " AND user2_id = " + user2_id + " AND quiz_id = " + quiz_id;
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        assertFalse(rs.next());
        System.out.println("Challenge can't be sent. Given two users aren't friends");
    }

    public void testAcceptChallenge() throws SQLException {
        String user1_id = "1";
        String user2_id = "2";
        String quiz_id = "1";
        userDAO.sendChallenge(user1_id, user2_id, quiz_id);
        userDAO.acceptChallenge(user1_id, user2_id, quiz_id);
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM challenges_table WHERE user1_id = " + user1_id + " AND user2_id = " + user2_id + " AND quiz_id = " + quiz_id;
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        rs.next();
        assertEquals("1", rs.getString("accepted"));
        System.out.println("Challenge accepted");
    }

    public void testGetSentMailsForUser1() throws SQLException {
        String user_id = "2";
        ArrayList<Mail> mails = userDAO.getSentMailsForUser(user_id);
        assertEquals(3, mails.size());
        assertEquals("2", mails.get(0).getSender_id());
        assertEquals("2", mails.get(1).getSender_id());
        assertEquals("2", mails.get(2).getSender_id());

        assertEquals("3", mails.get(0).getReceiver_id());
        assertEquals("3", mails.get(1).getReceiver_id());
        assertEquals("3", mails.get(2).getReceiver_id());

        assertEquals("Hi!", mails.get(0).getMail_text());
        assertEquals("I am coding", mails.get(1).getMail_text());
        assertEquals("and you?", mails.get(2).getMail_text());
    }

    public void testGetSentMailsForUser2() throws SQLException {
        String user_id = "3";
        ArrayList<Mail> mails = userDAO.getSentMailsForUser(user_id);
        assertEquals(3, mails.size());
        assertEquals("3", mails.get(0).getSender_id());
        assertEquals("3", mails.get(1).getSender_id());
        assertEquals("3", mails.get(2).getSender_id());

        assertEquals("2", mails.get(0).getReceiver_id());
        assertEquals("2", mails.get(1).getReceiver_id());
        assertEquals("2", mails.get(2).getReceiver_id());

        assertEquals("Hello!", mails.get(0).getMail_text());
        assertEquals("What are you doing?", mails.get(1).getMail_text());
        assertEquals("nothing at all", mails.get(2).getMail_text());
    }

    public void testGetReceivedMailsForUser2() throws SQLException {
        String user_id = "2";
        ArrayList<Mail> mails = userDAO.getReceivedMailsForUser(user_id);
        assertEquals(3, mails.size());
        assertEquals("3", mails.get(0).getSender_id());
        assertEquals("3", mails.get(1).getSender_id());
        assertEquals("3", mails.get(2).getSender_id());

        assertEquals("2", mails.get(0).getReceiver_id());
        assertEquals("2", mails.get(1).getReceiver_id());
        assertEquals("2", mails.get(2).getReceiver_id());


        assertEquals("Hello!", mails.get(0).getMail_text());
        assertEquals("What are you doing?", mails.get(1).getMail_text());
        assertEquals("nothing at all", mails.get(2).getMail_text());
    }

    public void testSendMail() throws SQLException {
        String sender_id = "2";
        String receiver_id = "3";
        String mail_text = "Hi!";
        userDAO.sendMail(sender_id, receiver_id, mail_text, null, null);
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM mails_table WHERE sender_id = ? AND receiver_id = ? AND mail_text = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, sender_id);
        statement.setString(2, receiver_id);
        statement.setString(3, mail_text);
        ResultSet rs = statement.executeQuery();
        assertTrue(rs.next());
        System.out.println("Mail send");
    }
    public void testGetPerformanceHistory() throws SQLException {
        ArrayList<Performance> set = userDAO.getUserPerformanceHistory("5", 100);
        assertEquals("1", set.get(0).getQuiz_id());
        assertEquals(80.0, set.get(0).getScore());
        System.out.println(set.get(0).getDate());
    }
    public void testGetUserPerformanceOnQuiz() throws SQLException {
        String user_id = "1";
        String quiz_id = "1";
        ArrayList<Performance> performances = userDAO.getUserPerformanceOnQuiz(user_id, quiz_id, 2, "date DESC");
        assertEquals("2024-06-26 18:20:00", performances.get(0).getDate());
        performances = userDAO.getUserPerformanceOnQuiz(user_id, quiz_id, 2, "used_time");
        assertEquals("00:02:00", performances.get(0).getUsed_time());
        performances = userDAO.getUserPerformanceOnQuiz(user_id, quiz_id, 2, "score DESC");
        assertEquals(100.0, performances.get(0).getScore());
    }

    public void testGetHighestPerformersOnQuiz() throws SQLException {
        String quiz_id = "1";
        ArrayList<Performance> performances = userDAO.getHighestPerformersOnQuiz(quiz_id, 10, false);
        for(int i = 1; i < performances.size(); i++) {
            assertEquals(Integer.toString(i), performances.get(i - 1).getUser_id());
        }
        performances = userDAO.getHighestPerformersOnQuiz(quiz_id, 10, true);
        assertEquals("5", performances.get(0).getUser_id());
        assertEquals("6", performances.get(1).getUser_id());

    }

    public void testGetAverageScoreOnQuiz() throws SQLException {
        String quiz_id = "2";
        assertEquals(75.0, userDAO.getAverageScoreOnQuiz(quiz_id));
    }

    public void testAddPerformance() {
        String user_id = "1";
        String quiz_id = "2";
        double score = 75;
        String usedTime = "00:00:00";
        try {
            userDAO.addPerformance(new Performance(quiz_id, score, null, user_id, usedTime));
            ArrayList<Performance> performances = userDAO.getUserPerformanceOnQuiz(user_id, quiz_id, 1, "used_time");
            assertEquals(75.0, performances.get(0).getScore());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
