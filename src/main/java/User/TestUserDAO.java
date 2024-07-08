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
        user = new User("duta1", "duta", "example@example", false);
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
        userDAO.addUser(user);
        String id = userDAO.getUserId(user.getUserName());
        User userClone = userDAO.getUser(id);
        assertEquals(userClone.getUserName(), user.getUserName());

        user = new User("mate", "mate", "example@example", false);
        userDAO.addUser(user);
        id = userDAO.getUserId(user.getUserName());
        userClone = userDAO.getUser(id);
        assertEquals("mate", userClone.getUserName());
    }

    public void testGetPerformanceHistory() throws SQLException {
        ArrayList<Performance> set = userDAO.getUserPerformanceHistory("1", 100);
        assertEquals("1", set.get(0).getQuiz_id());
        assertEquals(100.0, set.get(0).getScore());
        System.out.println(set.get(0).getDate());
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


    public void testSendMail1() throws SQLException {
        String sender_id = "1";
        String receiver_id = "2";
        String mail_text = "How are you?";
        userDAO.sendMail(sender_id, receiver_id, mail_text, null, null);
        String query = "SELECT * FROM mails_table WHERE sender_id = ? AND receiver_id = ? AND mail_text = ?";
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, sender_id);
        statement.setString(2, receiver_id);
        statement.setString(3, mail_text);
        ResultSet rs = statement.executeQuery();
        assertTrue(rs.next());
        System.out.println("Mail send");
    }

    public void testSendMail2() throws SQLException {
        String sender_id = "1";
        String receiver_id = "2";
        String mail_text = "Can you write a new class for mails?";
        String Subject = "OOP project";
        userDAO.sendMail(sender_id, receiver_id, mail_text, Subject, null);
        String query = "SELECT * FROM mails_table WHERE sender_id = ? AND receiver_id = ? AND mail_text = ? AND mail_subject = ?";
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, sender_id);
        statement.setString(2, receiver_id);
        statement.setString(3, mail_text);
        statement.setString(4, Subject);
        ResultSet rs = statement.executeQuery();
        assertTrue(rs.next());
        System.out.println("Mail send");
    }


    public void testGetSentMailsForUserTo1() throws SQLException {
        String user_id = "2";
        String receiver_id = "4";
        userDAO.sendMail("2", "4", "something", "Something", null);
        userDAO.sendMail("2", "4", "What?", "What?", null);
        userDAO.sendMail("2", "4", "Just Answer me", null, null);
        ArrayList<Mail> mails = userDAO.getSentMailsForUserTo(user_id, receiver_id);
        assertEquals(3, mails.size());
        assertEquals("2", mails.get(0).getSender_id());
        assertEquals("2", mails.get(1).getSender_id());
        assertEquals("2", mails.get(2).getSender_id());

        assertEquals("4", mails.get(0).getReceiver_id());
        assertEquals("4", mails.get(1).getReceiver_id());
        assertEquals("4", mails.get(2).getReceiver_id());

        assertEquals("something", mails.get(0).getMail_text());
        assertEquals("What?", mails.get(1).getMail_text());
        assertEquals("Just Answer me", mails.get(2).getMail_text());

        assertEquals("Something", mails.get(0).getMail_Subject());
        assertEquals("What?", mails.get(1).getMail_Subject());
        assertNull(mails.get(2).getMail_Subject());
    }

    public void testGetSentMailsForUserTo2() throws SQLException {
        String user_id = "2";
        String receiver_id = "3";
        ArrayList<Mail> mails = userDAO.getSentMailsForUserTo(user_id, receiver_id);
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

        assertNull(mails.get(0).getMail_Subject());
        assertNull(mails.get(1).getMail_Subject());
        assertNull(mails.get(2).getMail_Subject());
    }

    public void testGetReceivedMailsForUserFrom() throws SQLException {
        String user_id = "2";
        String sender_id = "3";
        ArrayList<Mail> mails = userDAO.getReceivedMailsForUserFrom(user_id, sender_id);
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

        assertNull(mails.get(0).getMail_Subject());
        assertNull(mails.get(1).getMail_Subject());
        assertNull(mails.get(2).getMail_Subject());
    }


    public void testGetMailById1() throws SQLException {
        String mail_id = "1";
        Mail mail = userDAO.getMailById(mail_id);
        assertEquals("1", mail.getMail_id());
        assertEquals("3", mail.getSender_id());
        assertEquals("2", mail.getReceiver_id());
        assertNull(mail.getMail_Subject());
        assertNull(mail.getHeadMail_id());
        assertEquals("Hello!", mail.getMail_text());
    }

    public void testGetMailById2() throws SQLException {
        String mail_id = "2";
        Mail mail = userDAO.getMailById(mail_id);
        assertEquals("2", mail.getMail_id());
        assertEquals("2", mail.getSender_id());
        assertEquals("3", mail.getReceiver_id());
        assertNull(mail.getMail_Subject());
        assertNull(mail.getHeadMail_id());
        assertEquals("Hi!", mail.getMail_text());
    }

    public void testDeleteMail1() throws SQLException {
        String mail_id = "8";
        userDAO.deleteMail(mail_id);
        Mail mail = userDAO.getMailById(mail_id);
        assertNull(mail);
    }

    public void testDeleteMail2() throws SQLException {
        String mail_id = "7";
        userDAO.deleteMail(mail_id);
        Mail mail = userDAO.getMailById(mail_id);
        assertNull(mail);
    }


    public void testGetReplaysForMail() throws SQLException {
        String mail_id = "1";
        userDAO.sendMail("2", "3", "heyy", null, "1");
        userDAO.sendMail("3", "2", "Can you come over?", null, "1");
        ArrayList<Mail> replaySet = userDAO.getReplaysForMail(mail_id);

        assertEquals(3, replaySet.size());

        assertEquals("Hello!", replaySet.get(0).getMail_text());
        assertEquals("heyy", replaySet.get(1).getMail_text());
        assertEquals("Can you come over?", replaySet.get(2).getMail_text());

        assertEquals("3", replaySet.get(0).getSender_id());
        assertEquals("2", replaySet.get(1).getSender_id());
        assertEquals("3", replaySet.get(2).getSender_id());

        assertEquals("2", replaySet.get(0).getReceiver_id());
        assertEquals("3", replaySet.get(1).getReceiver_id());
        assertEquals("2", replaySet.get(2).getReceiver_id());

        assertNull(replaySet.get(0).getMail_Subject());
        assertNull(replaySet.get(1).getMail_Subject());
        assertNull(replaySet.get(2).getMail_Subject());
    }

}
