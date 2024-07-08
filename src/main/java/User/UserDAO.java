package User;

import DataBaseConnectionPool.DataBaseConnectionPool;

import java.awt.image.AreaAveragingScaleFilter;
import java.sql.*;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO {
    public void addUser(User user) throws SQLException {
        System.out.println("Adding user");
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "INSERT INTO users_table (username, email, password) VALUES (?, ?, ?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setString(1, user.getUserName());
        stm.setString(2, user.getEmail());
        stm.setString(3, user.getPassword());
        stm.executeUpdate();
        DataBaseConnectionPool.getInstance().closeConnection(con);
    }

    public String getUserId(String username) throws SQLException{
        System.out.println("Getting user id");
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT user_id FROM users_table WHERE username = ?";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setString(1, username);
        ResultSet set = stm.executeQuery();
        String id = null;
        if(set.next()) {
            id = set.getString("user_id");
        }
        DataBaseConnectionPool.getInstance().closeConnection(con);
        return id;
    }

     public User getUser(String id) throws SQLException {
        System.out.println("Getting user");
        String query = "SELECT * FROM users_table WHERE user_id = ?";
        try (Connection con = DataBaseConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, id);
            try (ResultSet set = stm.executeQuery()) {
                if (set.next()) {
                    String username = set.getString("username");
                    String password = set.getString("password");
                    String email = set.getString("email");
                    return new User(username, password, email, true);
                }
            }
        } // try-with-resources will auto close Connection and PreparedStatement
        return null;
    }

    public void addPerformance(Performance performance) throws SQLException {
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "INSERT INTO performances_table (user_id, quiz_id, score, used_time) VALUES (?, ?, ?, ?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setString(1, performance.getUser_id());
        stm.setString(2, performance.getQuiz_id());
        stm.setDouble(3, performance.getScore());
        stm.setString(4, performance.getUsed_time());
        stm.executeUpdate();
        DataBaseConnectionPool.getInstance().closeConnection(con);
    }

    public ArrayList<Performance> getUserPerformanceHistory(String user_id, int size) throws SQLException{
        System.out.println("Getting user performance history");
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM performances_table WHERE user_id = ? ORDER BY date DESC;";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, user_id);
        ResultSet set = statement.executeQuery();
        ArrayList<Performance> result = new ArrayList<>();
        while(set.next()) {
            if(size == 0) break;
            String quiz_id = set.getString("quiz_id");
            double score = set.getDouble("score");
            String date = set.getString("date");
            String used_time = set.getString("used_time");
            result.add(new Performance(quiz_id, score, date, user_id, used_time));
            size--;
        }
        DataBaseConnectionPool.getInstance().closeConnection(con);
        return result;
    }

    public ArrayList<Performance> getUserPerformanceOnQuiz(String user_id, String quiz_id, int size, String order) throws SQLException {
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM performances_table WHERE user_id = ? AND quiz_id = ? ORDER BY " + order + ";";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, user_id);
        statement.setString(2, quiz_id);
        ResultSet set = statement.executeQuery();
        ArrayList<Performance> result = new ArrayList<>();
        while(set.next()) {
            if(size == 0) break;
            double score = set.getDouble("score");
            String date = set.getString("date");
            String used_time = set.getString("used_time");
            result.add(new Performance(quiz_id, score, date, user_id, used_time));
            size--;
        }
        DataBaseConnectionPool.getInstance().closeConnection(con);
        return result;
    }

    public ArrayList<Performance> getHighestPerformersOnQuiz(String quiz_id, int size, boolean last_day) throws SQLException {
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String subQueryDate = "(SELECT MAX(spt.date) FROM performances_table spt Where spt.user_id = pt.user_id AND spt.score = MAX(pt.score) AND spt.quiz_id = pt.quiz_id GROUP BY spt.user_id)";
        String subQueryUsedTime = "(SELECT MIN(spt.used_time) FROM performances_table spt WHERE spt.user_id = pt.user_id AND spt.score = MAX(pt.score) AND spt.quiz_id = pt.quiz_id GROUP BY spt.user_id) AS USED_TIME";
        String query = "SELECT user_id, MAX(score), " + subQueryUsedTime + ", " + subQueryDate +  " FROM performances_table pt WHERE pt.quiz_id = ? AND DATE(DATE_ADD(pt.date, INTERVAL 1 DAY)) >= DATE(CURRENT_DATE) GROUP BY pt.user_id ORDER BY MAX(pt.score) DESC, USED_TIME ASC";
        if(!last_day) {
            query = "SELECT user_id, MAX(score), " + subQueryUsedTime + " FROM performances_table pt WHERE quiz_id = ? GROUP BY user_id ORDER BY MAX(score) DESC, used_time ASC";
        }
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, quiz_id);
        ResultSet set = statement.executeQuery();
        ArrayList<Performance> result = new ArrayList<>();
        while(set.next()) {
            if(size == 0) break;
            double score = set.getDouble("MAX(score)");
            String user_id = set.getString("user_id");
            String used_time = set.getString("used_time");
            String date = null;
            if(last_day) date = set.getString(4);
            result.add(new Performance(quiz_id, score, date, user_id, used_time));
            size--;
        }
        DataBaseConnectionPool.getInstance().closeConnection(con);
        return result;
    }

    public double getAverageScoreOnQuiz(String quiz_id) throws SQLException {
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT AVG(score) FROM performances_table WHERE quiz_id = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, quiz_id);
        ResultSet set = statement.executeQuery();
        set.next();
        DataBaseConnectionPool.getInstance().closeConnection(con);
        return set.getDouble(1);
    }

    public ArrayList<String> getFriendsForUser(String user_id) throws SQLException {
        System.out.println("Getting friends for user");
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        ArrayList<String> result = new ArrayList<>();
        String query = "SELECT * FROM relations_table WHERE (user1_id = " + user_id + " OR user2_id = " + user_id + ") AND isPending = 0;";
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            String user1_id = rs.getString("user1_id");
            String user2_id = rs.getString("user2_id");
            if(!user1_id.equals(user_id)){
                result.add(user1_id);
            } else {
                result.add(user2_id);
            }
        }
        DataBaseConnectionPool.getInstance().closeConnection(con);
        return result;
    }

    public ArrayList<String> getFriendRequestsForUser(String user_id) throws SQLException {
        System.out.println("Getting friend requests for user");
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        ArrayList<String> result = new ArrayList<>();
        String query = "SELECT * FROM relations_table WHERE user2_id = " + user_id + " AND isPending = 1;";
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            String user1_id = rs.getString("user1_id");
            result.add(user1_id);
        }
        DataBaseConnectionPool.getInstance().closeConnection(con);
        return result;
    }

    public ArrayList<String> getSentRequestsForUser(String user_id) throws SQLException {
        System.out.println("Getting sent requests for user");
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        ArrayList<String> result = new ArrayList<>();
        String query = "SELECT * FROM relations_table WHERE user1_id = " + user_id + " AND isPending = 1;";
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            String user2_id = rs.getString("user2_id");
            result.add(user2_id);
        }
        DataBaseConnectionPool.getInstance().closeConnection(con);
        return result;
    }

    public boolean canSendFriendRequest(String sender_id, String reciever_id) throws SQLException {
        System.out.println("Checking if can send friend request");
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String queryTest = "SELECT * FROM relations_table WHERE (user1_id = " + sender_id + " AND user2_id = " + reciever_id + ") OR (user2_id = " + sender_id + " AND user1_id = " + reciever_id + ")";
        PreparedStatement statementTest = conn.prepareStatement(queryTest);
        ResultSet rs = statementTest.executeQuery();
        boolean ans = !rs.next();
        DataBaseConnectionPool.getInstance().closeConnection(conn);
        return ans;
    }

    public boolean sendFriendRequest(String sender_id, String reciever_id) throws SQLException {
        System.out.println("Sending friend request");
        if(canSendFriendRequest(sender_id, reciever_id)) {
            Connection con = DataBaseConnectionPool.getInstance().getConnection();
            String query = "INSERT INTO relations_table (user1_id, user2_id, isPending) VALUES (?, ?, 1)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, sender_id);
            statement.setString(2, reciever_id);
            statement.executeUpdate();
            DataBaseConnectionPool.getInstance().closeConnection(con);
            return true;
        }
        return false;
    }

    public boolean canAcceptFriendRequest(String sender_id, String reciever_id) throws SQLException {
        System.out.println("Checking if can accept friend request");
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM relations_table WHERE user1_id = " + sender_id + " AND user2_id = " + reciever_id + " AND isPending = 1";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        boolean ans = rs.next();
        DataBaseConnectionPool.getInstance().closeConnection(conn);
        return ans;
    }

    public void acceptFriendRequest(String sender_id, String reciever_id) throws SQLException {
        System.out.println("Accepting friend request");
        if(canAcceptFriendRequest(sender_id, reciever_id)){
            Connection conn = DataBaseConnectionPool.getInstance().getConnection();
            String query = "UPDATE relations_table SET isPending = 0 WHERE user1_id = " + sender_id + " AND user2_id = " + reciever_id;
            PreparedStatement statement = conn.prepareStatement(query);
            statement.executeUpdate();
            DataBaseConnectionPool.getInstance().closeConnection(conn);
        }
    }

    public void rejectFriendRequest(String sender_id, String reciever_id) throws SQLException {
        System.out.println("Rejecting friend request");
        if (canAcceptFriendRequest(sender_id, reciever_id)) {
            Connection conn = DataBaseConnectionPool.getInstance().getConnection();
            String query = "DELETE FROM relations_table WHERE user1_id = ? AND user2_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, sender_id);
            statement.setString(2, reciever_id);
            statement.executeUpdate();
            DataBaseConnectionPool.getInstance().closeConnection(conn);
            DataBaseConnectionPool.getInstance().closeConnection(conn);
        }
    }

    public boolean areFriends(String user1_id, String user2_id) throws SQLException {
        System.out.println("Checking if are friends");
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM relations_table WHERE (user1_id = " + user1_id + " AND user2_id = " + user2_id + " AND isPending = 0) OR (user1_id = " + user2_id + " AND user2_id = " + user1_id + " AND isPending = 0)";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        boolean ans = rs.next();
        DataBaseConnectionPool.getInstance().closeConnection(conn);
        return ans;
    }

    public void sendChallenge(String user1_id, String user2_id, String quiz_id) throws SQLException {
        System.out.println("Sending challenge");
        if(areFriends(user1_id, user2_id)){
            Connection conn = DataBaseConnectionPool.getInstance().getConnection();
            String query = "INSERT INTO challenges_table (quiz_id, user1_id, user2_id) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, quiz_id);
            statement.setString(2, user1_id);
            statement.setString(3, user2_id);
            statement.executeUpdate();
            DataBaseConnectionPool.getInstance().closeConnection(conn);
        }
    }

    private boolean canAcceptChallenge(String user1_id, String user2_id, String quiz_id) throws SQLException {
        System.out.println("Checking if can accept challenge");
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM challenges_table WHERE user1_id = " + user1_id + " AND user2_id = " + user2_id + " AND quiz_id = " + quiz_id;
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        boolean ans = rs.next();
        DataBaseConnectionPool.getInstance().closeConnection(conn);
        return ans;
    }

    public void acceptChallenge(String user1_id, String user2_id, String quiz_id) throws SQLException {
        System.out.println("Accepting challenge");
        if(canAcceptChallenge(user1_id, user2_id, quiz_id)){
            Connection conn = DataBaseConnectionPool.getInstance().getConnection();
            String query = "UPDATE challenges_table SET accepted = 1 WHERE user1_id = ? AND user2_id = ? AND quiz_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, user1_id);
            preparedStatement.setString(2, user2_id);
            preparedStatement.setString(3, quiz_id);
            preparedStatement.executeUpdate();
            DataBaseConnectionPool.getInstance().closeConnection(conn);
        }
    }

    public void rejectChallenge(String user1_id, String user2_id, String quiz_id) throws SQLException {
        System.out.println("Rejecting challenge");
        if(canAcceptChallenge(user1_id, user2_id, quiz_id)){
            Connection conn = DataBaseConnectionPool.getInstance().getConnection();
            String query = "DELETE FROM challenges_table WHERE user1_id = ? AND user2_id = ? AND quiz_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, user1_id);
            preparedStatement.setString(2, user2_id);
            preparedStatement.setString(3, quiz_id);
            preparedStatement.executeUpdate();
            DataBaseConnectionPool.getInstance().closeConnection(conn);
        }
    }

    public void sendMail(String sender_id, String receiver_id, String text, String Subject, String headMail_id) throws SQLException {
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "INSERT INTO mails_table (sender_id, receiver_id, mail_text, mail_subject, headMail_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, sender_id);
        statement.setString(2, receiver_id);
        statement.setString(3, text);
        statement.setString(4, Subject);
        statement.setString(5, headMail_id);
        statement.executeUpdate();
        DataBaseConnectionPool.getInstance().closeConnection(conn);
    }

    public ArrayList<Challenge> getChallengesSentForUser(String user_id) throws SQLException {
        System.out.println("Getting challenges sent for user");
        ArrayList<Challenge> result = new ArrayList<>();
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM challenges_table WHERE user2_id = " + user_id + " AND accepted = 0;";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            String user1_id = rs.getString("user1_id");
            String quiz_id = rs.getString("quiz_id");
            Challenge newChallenge = new Challenge(quiz_id, user1_id, user_id);
            result.add(newChallenge);
        }
        DataBaseConnectionPool.getInstance().closeConnection(conn);
        return result;
    }

    public ArrayList<Mail> getSentMailsForUser(String user_id) throws SQLException {
        System.out.println("Getting sent mails for user");
        ArrayList<Mail> result = new ArrayList<>();
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM mails_table WHERE sender_id = " + user_id + " AND headMail_id IS NULL ORDER BY send_date DESC, send_time DESC"; // + " AND topMail_id = NULL ORDER BY send_date DESC";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            String subject = rs.getString("mail_subject");
            String mail_text = rs.getString("mail_text");
            Date send_date = rs.getDate("send_date");
            String sender_id = rs.getString("sender_id");
            String receiver_id = rs.getString("receiver_id");
            String mail_id = rs.getString("mail_id");
            Time send_time = rs.getTime("send_time");
            String headMail_id = rs.getString("headMail_id");
            Mail newMail = new Mail(headMail_id, subject, mail_text, send_date, sender_id, receiver_id, mail_id, send_time);
            result.add(newMail);
        }
        DataBaseConnectionPool.getInstance().closeConnection(conn);
        return result;
    }

    public ArrayList<Mail> getSentMailsForUserTo(String user_id, String receiver_id) throws SQLException {
        ArrayList<Mail> result = new ArrayList<>();
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM mails_table WHERE sender_id = " + user_id + " AND receiver_id = " + receiver_id + " AND headMail_id IS NULL ORDER BY send_date DESC, send_time DESC";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            String subject = rs.getString("mail_subject");
            String mail_text = rs.getString("mail_text");
            Date send_date = rs.getDate("send_date");
            String senderId = rs.getString("sender_id");
            String receiverId = rs.getString("receiver_id");
            String mail_id = rs.getString("mail_id");
            Time send_time = rs.getTime("send_time");
            String headMail_id = rs.getString("headMail_id");
            Mail newMail = new Mail(headMail_id, subject, mail_text, send_date, senderId, receiverId, mail_id, send_time);
            result.add(newMail);
        }
        DataBaseConnectionPool.getInstance().closeConnection(conn);
        return result;
    }

    public ArrayList<Mail> getReceivedMailsForUser(String user_id) throws SQLException {
        System.out.println("Getting received mails for user");
        ArrayList<Mail> result = new ArrayList<>();
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM mails_table WHERE receiver_id = " + user_id + " AND headMail_id IS NULL ORDER BY send_date DESC, send_time DESC";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            String subject = rs.getString("mail_subject");
            String mail_text = rs.getString("mail_text");
            Date send_date = rs.getDate("send_date");
            String sender_id = rs.getString("sender_id");
            String receiver_id = rs.getString("receiver_id");
            String mail_id = rs.getString("mail_id");
            Time send_time = rs.getTime("send_time");
            String headMail_id = rs.getString("headMail_id");
            Mail newMail = new Mail(headMail_id, subject, mail_text, send_date, sender_id, receiver_id, mail_id, send_time);
            result.add(newMail);
        }
        DataBaseConnectionPool.getInstance().closeConnection(conn);
        return result;
    }

    public ArrayList<Mail> getReceivedMailsForUserFrom(String user_id, String sender_id) throws SQLException {
        ArrayList<Mail> result = new ArrayList<>();
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM mails_table WHERE receiver_id = " + user_id + " AND sender_id = " + sender_id + " AND headMail_id IS NULL ORDER BY send_date DESC, send_time DESC";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            String subject = rs.getString("mail_subject");
            String mail_text = rs.getString("mail_text");
            Date send_date = rs.getDate("send_date");
            String senderId = rs.getString("sender_id");
            String receiver_id = rs.getString("receiver_id");
            String mail_id = rs.getString("mail_id");
            Time send_time = rs.getTime("send_time");
            String headMail_id = rs.getString("headMail_id");
            Mail newMail = new Mail(headMail_id, subject, mail_text, send_date, senderId, receiver_id, mail_id, send_time);
            result.add(newMail);
        }
        DataBaseConnectionPool.getInstance().closeConnection(conn);
        return result;
    }

    public Mail getMail(String mail_id) throws SQLException {
        System.out.println("Getting mail");
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM mails_table WHERE mail_id = " + mail_id;
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            String mail_text = rs.getString("mail_text");
            Date send_date = rs.getDate("send_date");
            String sender_id = rs.getString("sender_id");
            String receiver_id = rs.getString("receiver_id");
            String mail_subject = rs.getString("mail_subject");
            Mail newMail = new Mail(mail_subject, mail_id, mail_text, send_date, sender_id, receiver_id);
            DataBaseConnectionPool.getInstance().closeConnection(conn);
            return newMail;
        } else {
            DataBaseConnectionPool.getInstance().closeConnection(conn);
        }
        return null;
    }

    public ArrayList<Performance> getFriendsPerformances(String user_id, int size) throws SQLException {
        System.out.println("Getting friends performances");
        String query = "SELECT * FROM performances_table " +
                "WHERE user_id IN (SELECT user1_id FROM relations_table WHERE user2_id = ? AND isPending = 0) " +
                "OR user_id IN (SELECT user2_id FROM relations_table WHERE user1_id = ? AND isPending = 0) " +
                "ORDER BY date DESC;";
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, user_id);
        statement.setString(2, user_id);
        ResultSet set = statement.executeQuery();
        ArrayList<Performance> result = new ArrayList<>();
        while(set.next()) {
            if(size == 0) break;
            String quiz_id = set.getString("quiz_id");
            double score = set.getDouble("score");
            String date = set.getString("date");
            String time = set.getString("used_time");
            result.add(new Performance(quiz_id, score, date, set.getString("user_id"), time));
            size--;
        }
        DataBaseConnectionPool.getInstance().closeConnection(conn);
        return result;
    }

    public Mail getMailById(String mail_id) throws SQLException {
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM mails_table WHERE mail_id = ?";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setString(1, mail_id);
        ResultSet rs = stm.executeQuery();
        Mail mail = null;
        if (rs.next()) {
            String subject = rs.getString("mail_subject");
            String mail_text = rs.getString("mail_text");
            Date send_date = rs.getDate("send_date");
            String sender_id = rs.getString("sender_id");
            String receiver_id = rs.getString("receiver_id");
            Time send_time = rs.getTime("send_time");
            String headMail_id = rs.getString("headMail_id");
            mail = new Mail(headMail_id, subject, mail_text, send_date, sender_id, receiver_id, mail_id, send_time);
        }
        DataBaseConnectionPool.getInstance().closeConnection(con);
        return mail;
    }

    public void deleteMail(String mail_id) throws SQLException {
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "DELETE FROM mails_table WHERE headMail_id = ?";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setString(1, mail_id);
        stm.executeUpdate();
        String headQuery = "DELETE FROM mails_table WHERE mail_id = ?";
        PreparedStatement statement = con.prepareStatement(headQuery);
        statement.setString(1, mail_id);
        statement.executeUpdate();
        DataBaseConnectionPool.getInstance().closeConnection(con);
    }


    public ArrayList<Mail>  getReplaysForMail(String mail_id) throws SQLException {
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM mails_table WHERE headMail_id = ? ORDER BY send_date, send_time";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setString(1, mail_id);
        ResultSet rs = stm.executeQuery();
        ArrayList<Mail> replays = new ArrayList<>();
        replays.add(getMailById(mail_id));
        while(rs.next()){
            String mail_text = rs.getString("mail_text");
            Date send_date = rs.getDate("send_date");
            String sender_id = rs.getString("sender_id");
            String receiver_id = rs.getString("receiver_id");
            String newmail_id = rs.getString("mail_id");
            Time send_time = rs.getTime("send_time");
            Mail newMail = new Mail(mail_id, null, mail_text, send_date, sender_id, receiver_id, newmail_id, send_time);
            replays.add(newMail);
        }
        DataBaseConnectionPool.getInstance().closeConnection(con);
        return replays;
    }

}
