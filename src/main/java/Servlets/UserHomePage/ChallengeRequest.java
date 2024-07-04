package Servlets.UserHomePage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import User.UserDAO;
import User.User;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ChallengeRequest")
public class ChallengeRequest extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        String friendId = request.getParameter("friend_id");
        String quizId = request.getParameter("quiz_id");
        String userId = (String) request.getSession().getAttribute("userId");
        UserDAO userDao = new UserDAO();

        if (action != null && friendId != null && userId != null) {
            if (action.equals("accept")) {
                System.out.println("accept");
                try {
                    userDao.acceptChallenge(friendId, userId, quizId);
                    response.sendRedirect("/quizPage?quiz_id=" + quizId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (action.equals("reject")) {
                System.out.println("reject");
                try {
                    userDao.rejectChallenge(friendId, userId, quizId);
                    response.sendRedirect("/UserHomePage");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
