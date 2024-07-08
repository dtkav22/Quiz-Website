package Servlets.UserHomePage;

import User.UserDAO;
import WebSockets.RequestWebSocket;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String friendId = request.getParameter("friend_id");
        String type = request.getParameter("type");
        String userId = (String) request.getSession().getAttribute("userId");
        UserDAO dao = new UserDAO();
        try {
            if ("friend".equals(type)) {
                System.out.println("Request added in db");
                dao.sendFriendRequest(userId, friendId);
                RequestWebSocket.sendFriendRequest(friendId, userId);
            } else if ("challenge".equals(type)) {
                String quizId = request.getParameter("quiz_id");
                System.out.println("Ideas are:" + userId + friendId + quizId);
                if(dao.areFriends(userId, friendId)) {
                    dao.sendChallenge(userId, friendId, quizId);
                    RequestWebSocket.sendChallengeRequest(friendId);
                } else {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"success\":true, \"message\":\"You are not friends.\"}");
                    return;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(friendId + " sent");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":true}");
    }
}
