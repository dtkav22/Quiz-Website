package User;

import DataBaseConnectionPool.DataBaseConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class UserDAO {
    public void addUser(User user) throws SQLException {
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "INSERT INTO users_table (username, email, password) VALUES (?, ?, ?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setString(1, user.getUserName());
        stm.setString(2, user.getEmail());
        stm.setString(3, user.getPassword());
        stm.executeUpdate();
        stm.executeUpdate();
    }

    public String getUserId(String username) throws SQLException{
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

    public User getUser(String id) throws SQLException{
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM users_table WHERE user_id = ?";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setString(1, id);
        ResultSet set = stm.executeQuery();
        User user = null;
        if (set.next()) {
            String username = set.getString("username");
            String password = set.getString("password");
            String email = set.getString("email");
            user = new User(username, password, email, true);
        }
        DataBaseConnectionPool.getInstance().closeConnection(con);
        return user;
    }

    public ArrayList<Performance> getUserPerformanceHistory(String user_id, int size) throws SQLException{
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
            result.add(new Performance(quiz_id, score, date));
            size--;
        }
        DataBaseConnectionPool.getInstance().closeConnection(con);
        return result;
    }

    public ArrayList<String> getFriendsForUser(String user_id) throws SQLException {
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
        Connection con = DataBaseConnectionPool.getInstance().getConnection();
        ArrayList<String> result = new ArrayList<>();
        String query = "SELECT * FROM relations_table WHERE user1_id = " + user_id + " AND isPending = 1;";
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            String user2_id = rs.getString("user2_id");
            result.add(user2_id);
        }
        con.close();
        return result;
    }

    private boolean canSendFriendRequest(String sender_id, String reciever_id) throws SQLException {
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String queryTest = "SELECT * FROM relations_table WHERE (user1_id = " + sender_id + " AND user2_id = " + reciever_id + ") OR (user2_id = " + sender_id + " AND user1_id = " + reciever_id + ")";
        PreparedStatement statementTest = conn.prepareStatement(queryTest);
        ResultSet rs = statementTest.executeQuery();
        boolean ans = !rs.next();
        DataBaseConnectionPool.getInstance().closeConnection(conn);
        return ans;
    }

    public boolean sendFriendRequest(String sender_id, String reciever_id) throws SQLException {
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

    private boolean canAcceptFriendRequest(String sender_id, String reciever_id) throws SQLException {
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM relations_table WHERE user1_id = " + sender_id + " AND user2_id = " + reciever_id + " AND isPending = 1";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        boolean ans = rs.next();
        DataBaseConnectionPool.getInstance().closeConnection(conn);
        return ans;
    }

    public void acceptFriendRequest(String sender_id, String reciever_id) throws SQLException {
        if(canAcceptFriendRequest(sender_id, reciever_id)){
            Connection conn = DataBaseConnectionPool.getInstance().getConnection();
            String query = "UPDATE relations_table SET isPending = 0 WHERE user1_id = " + sender_id + " AND user2_id = " + reciever_id;
            PreparedStatement statement = conn.prepareStatement(query);
            statement.executeUpdate();
            DataBaseConnectionPool.getInstance().closeConnection(conn);
        }
    }

    public boolean areFriends(String user1_id, String user2_id) throws SQLException {
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM relations_table WHERE (user1_id = " + user1_id + " AND user2_id = " + user2_id + " AND isPending = 0) OR (user1_id = " + user2_id + " AND user2_id = " + user1_id + " AND isPending = 0)";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        boolean ans = rs.next();
        DataBaseConnectionPool.getInstance().closeConnection(conn);
        return ans;
    }

    public void sendChallenge(String user1_id, String user2_id, String quiz_id) throws SQLException {
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
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM challenges_table WHERE user1_id = " + user1_id + " AND user2_id = " + user2_id + " AND quiz_id = " + quiz_id;
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        boolean ans = rs.next();
        DataBaseConnectionPool.getInstance().closeConnection(conn);
        return ans;
    }

    public void acceptChallenge(String user1_id, String user2_id, String quiz_id) throws SQLException {
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

    public void sendMail(String sender_id, String receiver_id, String text) throws SQLException {
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "INSERT INTO mails_table (sender_id, receiver_id, mail_text) VALUES (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, sender_id);
        statement.setString(2, receiver_id);
        statement.setString(3, text);
        statement.executeUpdate();
        DataBaseConnectionPool.getInstance().closeConnection(conn);
    }

    public ArrayList<Challenge> getChallengesSentForUser(String user_id) throws SQLException {
        ArrayList<Challenge> result = new ArrayList<>();
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM challenges_table WHERE user2_id = " + user_id;
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
        ArrayList<Mail> result = new ArrayList<>();
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM mails_table WHERE sender_id = " + user_id + " ORDER BY send_date DESC";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            String subject = rs.getString("mail_subject");
            String mail_text = rs.getString("mail_text");
            Date send_date = rs.getDate("send_date");
            String sender_id = rs.getString("sender_id");
            String receiver_id = rs.getString("receiver_id");
            String mail_id = rs.getString("mail_id");
            Mail newMail = new Mail(subject, mail_text, send_date, sender_id, receiver_id, mail_id);
            result.add(newMail);
        }
        DataBaseConnectionPool.getInstance().closeConnection(conn);
        return result;
    }

    public ArrayList<Mail> getReceivedMailsForUser(String user_id) throws SQLException {
        ArrayList<Mail> result = new ArrayList<>();
        Connection conn = DataBaseConnectionPool.getInstance().getConnection();
        String query = "SELECT * FROM mails_table WHERE receiver_id = " + user_id + " ORDER BY send_date DESC";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            String subject = rs.getString("mail_subject");
            String mail_text = rs.getString("mail_text");
            Date send_date = rs.getDate("send_date");
            String sender_id = rs.getString("sender_id");
            String receiver_id = rs.getString("receiver_id");
            int mail_id = rs.getInt("mail_id");
            String mail_id_str = "" + mail_id;
            Mail newMail = new Mail(subject, mail_text, send_date, sender_id, receiver_id, mail_id_str);
            result.add(newMail);
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
            mail = new Mail(subject, mail_text, send_date, sender_id, receiver_id, mail_id);
        }
        DataBaseConnectionPool.getInstance().closeConnection(con);
        return mail;
    }
}
