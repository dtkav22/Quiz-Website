package Servlets.UserHomePage;

import User.UserDAO;
import WebSockets.FriendsRequestWebSocket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/SendRequest")
public class SendRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String friendId = request.getParameter("friend_id");
        String type = request.getParameter("type");
        String userId = (String) request.getSession().getAttribute("userId");
        UserDAO dao = new UserDAO();
        try {
            System.out.println("not not request added in db");
            if(type.equals("friend")) {
                System.out.println("request added in db");
                dao.sendFriendRequest(userId, friendId);
            } else if(type.equals("challenge")) {
                String quizId = request.getParameter("quiz_id");
                dao.sendChallenge(userId, friendId, quizId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(friendId + "sent");
        FriendsRequestWebSocket.sendFriendRequest(friendId);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":" + true + "}");
    }
}
